package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.Applicant;

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
			System.out.println("1. Insert application");
			System.out.println("2. Delete application");
			System.out.println("3. Update applicant major");
			System.out.println("4. Show company matching description");
			System.out.println("5. Show all companies hiring");
			System.out.println("6. Show all applications from an applicant");
			System.out.println("7. Find number of applications an applicant sent");
			System.out.println("8. Find companies whose interns have average > 1 work term");
			System.out.println("9. Find applicants who applied to every company");
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
					handleSelectionOption();
					break;
				case 5:
					handleProjectionOption();
					break;
				case 6:
					handleJoinOption();
					break;
				case 7:
					handleAggregationOption();
					break;
				case 8:
					handleNestedAggregationOption();
					break;
				case 9:
					handleDivisionOption();
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

	private void handleDivisionOption() {
	}

	private void handleNestedAggregationOption() {
	}

	private void handleAggregationOption() {
	}

	private void handleJoinOption() {
	}

	private void handleProjectionOption() {
	}

	private void handleSelectionOption() {
	}

	private void handleDeleteOption() {
		int sin = INVALID_INPUT;
		while (sin == INVALID_INPUT) {
			System.out.print("Please enter the SIN of the applicant you wish to delete: ");
			sin = readInteger(false);
			if (sin != INVALID_INPUT) {
				delegate.deleteBranch(sin);
			}
		}
	}
	
	private void handleInsertOption() {
		int sin = INVALID_INPUT;
		while (sin == INVALID_INPUT) {
			System.out.print("Please enter the SIN of the applicant you wish to insert: ");
			sin = readInteger(false);
		}

		int year = INVALID_INPUT;
		while (year == INVALID_INPUT) {
			System.out.print("Please enter the year of the applicant you wish to insert: ");
			year = readInteger(false);
		}
		
		String major = null;
		while (major == null || major.length() <= 0) {
			System.out.print("Please enter the major of the applicant you wish to insert: ");
			major = readLine().trim();
		}

		String firstName = null;
		while (firstName == null || firstName.length() <= 0) {
			System.out.print("Please enter the first name of the applicant you wish to insert: ");
			firstName = readLine().trim();
		}

		String lastName = null;
		while (lastName == null || lastName.length() <= 0) {
			System.out.print("Please enter the lastName you wish to insert: ");
			lastName = readLine().trim();
		}

		String address = null;
		while (address == null || address.length() <= 0) {
			System.out.print("Please enter the address you wish to insert: ");
			address = readLine().trim();
		}
		
		Integer doe = null;
		while (doe == null) {
			System.out.print("Please enter the year of birth you wish to insert: ");
			doe = readInteger(true);
		}
		
		Applicant model = new Applicant(sin, year, major, firstName, lastName, address, doe);
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
		int sin = INVALID_INPUT;
		while (sin == INVALID_INPUT) {
			System.out.print("Please enter SIN of applicant you wish to update: ");
			sin = readInteger(false);
		}
		
		String major = null;
		while (major == null || major.length() <= 0) {
			System.out.print("Please enter the major you wish to update to: ");
			major = readLine().trim();
		}

		delegate.updateBranch(sin, major);
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

