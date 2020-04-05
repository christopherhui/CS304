package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.Job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert job");
			System.out.println("2. Delete job");
			System.out.println("3. Update application status");
			System.out.println("4. Show job titles from specific company");
			System.out.println("5. Show company names from job postings");
			System.out.println("6. Show locations for specific job position");
			System.out.println("7. Show total number of jobs available");
			System.out.println("8. Show total number of jobs from each company");
			System.out.println("9. Find SIN of job applicant who applied to all companies");
			System.out.println("10. Quit");
			System.out.print("Please choose one of the above options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:
					handleShowJobsFromCompany();
					break;
				case 5:
					handleShowCompanyNames();
					break;
				case 6:
					handleShowLocations();
					break;
				case 7:
					handleShowTotalNumJobs();
					break;
				case 8:
					handleShowTotalJobsPerCompany();
					break;
				case 9:
					handleShowSinAllJobs();
					break;
				case 10:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}

	private void handleShowSinAllJobs() {
	}

	private void handleShowTotalJobsPerCompany() {
	}

	private void handleShowTotalNumJobs() {
	}

	private void handleShowLocations() {
	}

	private void handleShowCompanyNames() {
	}

	private void handleShowJobsFromCompany() {
	}

	private void handleDeleteOption() {
		int branchId = INVALID_INPUT;
		while (branchId == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to delete: ");
			branchId = readInteger(false);
			if (branchId != INVALID_INPUT) {
				delegate.deleteBranch(branchId);
			}
		}
	}
	
	private void handleInsertOption() {
		int jobNo = INVALID_INPUT;
		while (jobNo == INVALID_INPUT) {
			System.out.print("Please enter the job number you wish to insert: ");
			jobNo = readInteger(false);
		}
		
		String companyName = null;
		while (companyName == null || companyName.length() <= 0) {
			System.out.print("Please enter the company name you wish to insert: ");
			companyName = readLine().trim();
		}
		
		// branch address is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the job title you wish to insert: ");
		String jobTitle = readLine().trim();
		if (jobTitle.length() == 0) {
			jobTitle = null;
		}
		
		String description = null;
		while (description == null || description.length() <= 0) {
			System.out.print("Please enter the description you wish to insert: ");
			description = readLine().trim();
		}
		
//		int phoneNumber = INVALID_INPUT;
//		while (phoneNumber == INVALID_INPUT) {
//			System.out.print("Please enter the branch phone number you wish to insert: ");
//			phoneNumber = readInteger(true);
//		}
		
		Job model = new Job(jobNo, companyName, jobTitle, description);
//		delegate.insertBranch(model);
	}
	
	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalTransactionsFinished();
	}
	
	private void handleUpdateOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to update: ");
			id = readInteger(false);
		}
		
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to update: ");
			name = readLine().trim();
		}

		delegate.updateBranch(id, name);
	}
	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
