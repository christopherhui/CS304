package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Applicant;
import ca.ubc.cs304.model.BranchModel;

import java.sql.*;
import java.util.ArrayList;

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

	public void deleteApplicant(int sin) {
		ArrayList<Applicant> result = new ArrayList<Applicant>();
		try {
			PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM APPLICANT4 a4, APPLICANT3 a3 WHERE a4.SIN = ? AND a4.FIRSTNAME = a3.FIRSTNAME");
			ps4.setInt(1, sin);
			ResultSet rs = ps4.executeQuery();

			while(rs.next()) {
				Applicant model = new Applicant(rs.getInt("SIN"), rs.getInt("ProgramYear"),
						rs.getString("Major"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Address"), doe);
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
	
	public void insertApplicant(Applicant applicant) {
		try {
			PreparedStatement ps4 = connection.prepareStatement("INSERT INTO APPLICANT4 VALUES (?,?,?,?,?)");
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
			if (applicant.getDoe() == null) {
			    ps1.setNull(3, java.sql.Types.DATE);
            } else {
			    ps1.setDate(3, applicant.getDoe());
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
	
	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
		
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
				BranchModel model = new BranchModel(rs.getString("branch_addr"),
													rs.getString("branch_city"),
													rs.getInt("branch_id"),
													rs.getString("branch_name"),
													rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}	
		
		return result.toArray(new BranchModel[result.size()]);
	}

	public void updateApplicantMajor(int sin, String major) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE APPLICANT4 SET MAJOR = ? WHERE SIN = ?");
            ps.setString(1, major);
            ps.setInt(2, sin);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
//                System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
	
	public void updateBranch(int id, String name) {
		try {
		  PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
		  ps.setString(1, name);
		  ps.setInt(2, id);
		
		  int rowCount = ps.executeUpdate();
		  if (rowCount == 0) {
		      System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
		  }
	
		  connection.commit();
		  
		  ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}	
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
