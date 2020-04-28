package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			login("REMOVED", "REMOVED");
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	// Delete query
	public String deleteApplicant(int sin) {
		ArrayList<Applicant> result = new ArrayList<Applicant>();
		try {
			PreparedStatement ps4 = connection.prepareStatement("SELECT DISTINCT * FROM APPLICANT4 a4, APPLICANT3 a3, APPLICANT1 a1 WHERE a4.SIN = ? AND a4.FIRSTNAME = a3.FIRSTNAME AND a1.ADDRESS = a4.ADDRESS");
			ps4.setInt(1, sin);
			ResultSet rs = ps4.executeQuery();

			if (rs.next()) {
				Applicant model = new Applicant(rs.getInt("SIN"), rs.getInt("ProgramYear"),
						rs.getString("Major"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), -1);
				result.add(model);
			}

			PreparedStatement ps1 = connection.prepareStatement("DELETE FROM APPLICANT1 a1 WHERE a1.FIRSTNAME = ? AND a1.ADDRESS = ?");
			PreparedStatement ps3 = connection.prepareStatement("DELETE FROM APPLICANT3 a3 WHERE a3.FIRSTNAME = ? AND a3.LASTNAME = ?");
			ps4 = connection.prepareStatement("DELETE FROM APPLICANT4 a4 WHERE a4.SIN = ?");
			for (Applicant a : result) {
				// Set values of each query
				ps1.setString(1, a.getFirstName());
				ps1.setString(2, a.getAddress());
				ps3.setString(1, a.getFirstName());
				ps3.setString(2, a.getLastName());
				ps4.setInt(1, a.getSin());

				// Execute such updates
				ps1.executeUpdate();
				ps3.executeUpdate();
				ps4.executeUpdate();
			}

			connection.commit();

			// Close connections
			rs.close();
			ps1.close();
			ps3.close();
			ps4.close();
			return "Success";
		} catch (SQLException e) {
			rollbackConnection();
			return EXCEPTION_TAG + " " + e.getMessage();
		}
	}

	// Insert query
	public String insertApplicant(Applicant applicant) {
		try {
			PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Degree WHERE Major = ? AND ProgramYear = ?");
			ps2.setString(1, applicant.getMajor());
			ps2.setInt(2, applicant.getYear());
			ResultSet rs = ps2.executeQuery();

			if (rs.next()) {
				// Do nothing
			}
			else {
				PreparedStatement ps5 = connection.prepareStatement("INSERT INTO DEGREE VALUES (?,?,?)");
				ps5.setInt(1, applicant.getYear());
				ps5.setString(2,applicant.getMajor());
				ps5.setNull(3, java.sql.Types.CHAR);
				ps5.executeUpdate();
				connection.commit();
			}

			PreparedStatement ps1 = connection.prepareStatement("INSERT INTO APPLICANT1 VALUES (?,?,?)");
			ps1.setString(1, applicant.getFirstName());
			ps1.setString(2, applicant.getAddress());
			if (applicant.getDoe() == -1) {
				ps1.setNull(3, Types.INTEGER);
			} else {
				ps1.setInt(3, applicant.getDoe());
			}

			ps1.executeUpdate();

			PreparedStatement ps3 = connection.prepareStatement("INSERT INTO APPLICANT3 VALUES (?,?,?)");
			ps3.setString(1, applicant.getFirstName());
			ps3.setString(2, applicant.getLastName());
			ps3.setString(3, applicant.getAddress());

			ps3.executeUpdate();

			PreparedStatement ps4 = connection.prepareStatement("INSERT INTO APPLICANT4 VALUES (?,?,?,?,?,?)");
			ps4.setInt(1, applicant.getSin());
			ps4.setInt(2, applicant.getYear());
			ps4.setString(3, applicant.getMajor());
			ps4.setString(4, applicant.getFirstName());
			ps4.setString(5, applicant.getLastName());
			ps4.setString(6, applicant.getAddress());

			ps4.executeUpdate();
			connection.commit();

			ps4.close();
			ps1.close();
			ps3.close();
			return "Success";
		} catch (SQLException e) {
			rollbackConnection();
			return EXCEPTION_TAG + " " + e.getMessage();
		}
	}

	// Update query
	public String updateApplicantMajor(int sin, String major, int year) {
		try {
			PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Degree WHERE Major = ? AND ProgramYear = ?");
			ps2.setString(1, major);
			ps2.setInt(2, year);
			ResultSet rs = ps2.executeQuery();

			if (rs.next()) {
				// Do nothing
			}
			else {
				PreparedStatement ps5 = connection.prepareStatement("INSERT INTO DEGREE VALUES (?,?,?)");
				ps5.setInt(1, year);
				ps5.setString(2, major);
				ps5.setNull(3, java.sql.Types.CHAR);
				ps5.executeUpdate();
				connection.commit();
			}

			PreparedStatement ps = connection.prepareStatement("UPDATE APPLICANT4 SET MAJOR = ?, PROGRAMYEAR = ? WHERE SIN = ?");
			ps.setString(1, major);
			ps.setInt(2, year);
			ps.setInt(3, sin);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Applicant " + sin + " does not exist!");
			}

			connection.commit();
			ps.close();
			return "Success";
		} catch (SQLException e) {
			rollbackConnection();
			return EXCEPTION_TAG + " " + e.getMessage();
		}
	}

	// Projection query, returns all company names that are hiring in sorted order
	public List<Company> getCompanyHiringInfo(String filterFirst, String filterSecond, String filterThird) {
		List<Company> result = new ArrayList<>();

		try {
			int start = 0;
			if (filterFirst.equals("COMPANY_NAME")) {
				start |= 1;
			}
			if (filterFirst.equals("TYPE")) {
				start |= 2;
			}
			if (filterFirst.equals("HIRINGAMT")) {
				start |= 4;
			}
			if (filterSecond.equals("COMPANY_NAME")) {
				start |= 1;
			}
			if (filterSecond.equals("TYPE")) {
				start |= 2;
			}
			if (filterSecond.equals("HIRINGAMT")) {
				start |= 4;
			}
			if (filterThird.equals("COMPANY_NAME")) {
				start |= 1;
			}
			if (filterThird.equals("TYPE")) {
				start |= 2;
			}
			if (filterThird.equals("HIRINGAMT")) {
				start |= 4;
			}
			if (start == 1) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT COMPANY_NAME FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(rs.getString("COMPANY_NAME"), -1, null);
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 2) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT TYPE FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(null, -1, rs.getString("TYPE"));
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 4) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT HIRINGAMT FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(null, rs.getInt("HIRINGAMT"), null);
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 7) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT COMPANY_NAME, HIRINGAMT, TYPE FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(rs.getString("COMPANY_NAME"), rs.getInt("HIRINGAMT"), rs.getString("TYPE"));
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 5) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT COMPANY_NAME, HIRINGAMT FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(rs.getString("COMPANY_NAME"), rs.getInt("HIRINGAMT"), null);
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 3) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT COMPANY_NAME, TYPE FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(rs.getString("Company_Name"), -1, rs.getString("Type"));
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
			if (start == 6) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT HIRINGAMT, TYPE FROM COMPANY");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Company comp = new Company(null, rs.getInt("HIRINGAMT"), rs.getString("TYPE"));
					result.add(comp);
				}

				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result;
	}

	// All applicants with a major of a specific value
	public List<Applicant> getAllApplicants(String cname) {
		ArrayList<Applicant> result = new ArrayList<Applicant>();
		try {
			PreparedStatement ps4 = connection.prepareStatement("SELECT DISTINCT * FROM APPLICANT4 a4 WHERE a4.MAJOR = ?");
			ps4.setString(1, cname);
			ResultSet rs = ps4.executeQuery();

			while (rs.next()) {
				Applicant model = new Applicant(rs.getInt("SIN"), rs.getInt("ProgramYear"),
						rs.getString("Major"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), -1);
				result.add(model);
			}

			ps4.close();
		} catch (SQLException e) {
			rollbackConnection();
		}
		return result;
	}

	// Selection query, finds all companies and jobs that match keywords in a description
	public List<Job> getCompanyMatchingDescription(String filter, int status) {
		List<Job> result = new ArrayList<>();

		try {
			if (status == 0) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT * FROM JOB WHERE DESCRIPTION LIKE ?");
				ps.setString(1, "%" + filter + "%");
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					Job job = new Job(rs.getInt("Job_Number"), rs.getString("Company_Name"),
							rs.getString("Job_Title"), rs.getString("Description"));
					result.add(job);
				}

				rs.close();
				ps.close();
			}
			if (status == 1) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT * FROM JOB WHERE COMPANY_NAME LIKE ?");
				ps.setString(1,"%" + filter + "%");
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					Job job = new Job(rs.getInt("Job_Number"), rs.getString("Company_Name"),
							rs.getString("Job_Title"), rs.getString("Description"));
					result.add(job);
				}

				rs.close();
				ps.close();
			}
			if (status == 2) {
				PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT * FROM JOB WHERE JOB_TITLE LIKE ?");
				ps.setString(1, "%" + filter + "%");
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					Job job = new Job(rs.getInt("Job_Number"), rs.getString("Company_Name"),
							rs.getString("Job_Title"), rs.getString("Description"));
					result.add(job);
				}
				rs.close();
				ps.close();
			}

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result;
	}

	// Join query, for a company, find all applications from an applicant,
	// including the platform, their degree, and school.
	// Returns a pairing of the applicant details (first), and the details of the app id and platform name (second)
	public List<Pair<Applicant, ApplicationThroughFor>> findAllApplicants(String Company_Name) {
		List<Pair<Applicant, ApplicationThroughFor>> result = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT a.SIN, a.FIRSTNAME, a.LASTNAME, a.ADDRESS, " +
					"a.MAJOR, a.PROGRAMYEAR, atf.PLATFORM_NAME, atf.APP_ID " +
					"FROM JOB j, APPLICATION_THROUGH_FOR atf, APPLICANT4 a " +
					"WHERE j.COMPANY_NAME = ? " +
					"AND atf.COMPANY_NAME = j.COMPANY_NAME " +
					"AND atf.SIN = a.SIN");
			ps.setString(1, Company_Name);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Applicant applicant = new Applicant(-1, rs.getInt("ProgramYear"), rs.getString("Major"),
						rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), -1);
				ApplicationThroughFor applicationThroughFor = new ApplicationThroughFor(rs.getInt("SIN"), Company_Name,
						rs.getString("App_ID"), rs.getString("Platform_Name"));
				result.add(new Pair<>(applicant, applicationThroughFor));
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	// Aggregation query - Finds the number of applications an applicant has sent
	public int findNoApps(int SIN) {
		int result = -1;
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM APPLICATION_THROUGH_FOR a4 " +
					"WHERE SIN = ?");
			ps.setInt(1, SIN);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				result = rs.getInt("total");
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	// Nested Aggregation with Group-By - Returns the companies in which interns who apply there on average have completed
	// more than 1.5 terms.
	// We also want companies that have been applied to by at least 2 interns
	public List<Company> findHireAvgTermsWorked() {
		List<Company> result = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT c.COMPANY_NAME, c.HIRINGAMT, c.TYPE " +
					"FROM COMPANY c, APPLICATION_THROUGH_FOR atf, INTERN i " +
					"WHERE c.COMPANY_NAME = atf.COMPANY_NAME AND atf.SIN = i.SIN " +
					"GROUP BY c.COMPANY_NAME, c.HIRINGAMT, c.TYPE " +
					"HAVING COUNT(i.SIN) >= 2 AND AVG(i.NUMBER_OF_TERMS) >= 1.5");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Company company = new Company(rs.getString("Company_Name"), rs.getInt("HiringAmt"), rs.getString("Type"));
				result.add(company);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	// Division - Returns a list of applicants who have applied to every company possible
	public List<Applicant> appliedToAll() {
		List<Applicant> result = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT a4.SIN, PROGRAMYEAR, MAJOR, FIRSTNAME, LASTNAME, ADDRESS FROM " +
					"APPLICATION_THROUGH_FOR atf, APPLICANT4 a4 " +
					"WHERE atf.SIN = a4.SIN AND NOT EXISTS " +
					"(SELECT COMPANY_NAME FROM COMPANY MINUS " +
					"(SELECT DISTINCT COMPANY_NAME FROM APPLICATION_THROUGH_FOR atf2 WHERE atf2.SIN = atf.SIN))");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Applicant applicant = new Applicant(rs.getInt("SIN"), rs.getInt("ProgramYear"), rs.getString("Major"),
						rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), -1);
				result.add(applicant);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteBranch(int branchId) {
	}

	public void updateBranch(int branchId, String name) {
	}
}
