package ca.ubc.cs304.database;

import ca.ubc.cs304.controller.Application;
import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
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
	public void deleteApplicant(int sin) {
		ArrayList<Applicant> result = new ArrayList<Applicant>();
		try {
			PreparedStatement ps4 = connection.prepareStatement("SELECT DISTINCT * FROM APPLICANT4 a4, APPLICANT3 a3, APPLICANT1 a1 WHERE a4.SIN = ? AND a4.FIRSTNAME = a3.FIRSTNAME AND a1.ADDRESS = a4.ADDRESS");
			ps4.setInt(1, sin);
			ResultSet rs = ps4.executeQuery();

			while(rs.next()) {
				Applicant model = new Applicant(rs.getInt("SIN"), rs.getInt("ProgramYear"),
						rs.getString("Major"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), rs.getInt("DateOfBirth"));
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

			// Close connections
			rs.close();
			ps1.close();
			ps3.close();
			ps4.close();

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Insert query
	public void insertApplicant(Applicant applicant) {
		try {
			PreparedStatement ps4 = connection.prepareStatement("INSERT INTO APPLICANT4 VALUES (?,?,?,?,?,?)");
			ps4.setInt(1, applicant.getSin());
			ps4.setInt(2, applicant.getYear());
			ps4.setString(3, applicant.getMajor());
			ps4.setString(4, applicant.getFirstName());
			ps4.setString(5, applicant.getAddress());

			ps4.executeUpdate();
			connection.commit();

			PreparedStatement ps1 = connection.prepareStatement("INSERT INTO APPLICANT1 VALUES (?,?,?)");
			ps1.setString(1, applicant.getFirstName());
			ps1.setString(2, applicant.getAddress());
			if (applicant.getDoe() == -1) {
			    ps1.setNull(3, Types.INTEGER);
            } else {
			    ps1.setInt(3, applicant.getDoe());
            }

			ps1.executeUpdate();
			connection.commit();

			PreparedStatement ps3 = connection.prepareStatement("INSERT INTO APPLICANT3 VALUES (?,?,?)");
			ps3.setString(1, applicant.getFirstName());
			ps3.setString(2, applicant.getLastName());
			ps3.setString(3, applicant.getAddress());

			ps3.executeUpdate();
			connection.commit();

			ps4.close();
            ps1.close();
			ps3.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Update query
	public void updateApplicantMajor(int sin, String major) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE APPLICANT4 SET MAJOR = ? WHERE SIN = ?");
			ps.setString(1, major);
			ps.setInt(2, sin);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Applicant " + sin + " does not exist!");
			}

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Projection query, returns all company names that are hiring in sorted order
	public List<Company> getCompanyHiringInfo(CompEnum filterFirst, CompEnum filterSecond) {
		List<Company> result = new ArrayList<>();
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT ?, ? FROM COMPANY");
            ps.setString(1, String.valueOf(filterFirst));
            ps.setString(2, String.valueOf(filterSecond));
            ResultSet rs = ps.executeQuery();

//    		// get info on ResultSet
//    		ResultSetMetaData rsmd = rs.getMetaData();
//
//    		System.out.println(" ");
//
//    		// display column names;
//    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
//    			// get column name and print it
//    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//    		}
			
			while(rs.next()) {
				Company comp = new Company(rs.getString("Company_Name"), -1, null);
				result.add(comp);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}	
		
		return result;
	}

	// Selection query, finds all companies and jobs that match keywords in a description
	public List<Job> getCompanyMatchingDescription(String filter) {
		List<Job> result = new ArrayList<>();

		try {
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
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total FROM APPLICANT4 a4 " +
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

	// Nested Aggregation with Group-By - Returns the companies that hire interns that have completed above 1 work terms, on average.
	// We also want companies that have at least hired at least 2 interns.
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
			PreparedStatement ps = connection.prepareStatement("SELECT a4.SIN, PROGRAMYEAR, MAJOR, FIRSTNAME, LASTNAME, ADDRESS FROM " +
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
}
