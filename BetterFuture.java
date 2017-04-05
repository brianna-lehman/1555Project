public class BetterFuture {
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		System.out.println("What type of user are you?");
		System.out.println("\t1.\tAdministrator\n\t2.\tCustomer");
		String user = kb.next();

		if (user.compareToIgnoreCase("administrator") == 0) {
			adminInterface();
		}
		else if (user.compareToIgnoreCase("customer") == 0) {
			customerInterface();
		}
		else {
			System.out.println("Incorrect input.");
		}
	}

	public static void customerInterface() {
		Scanner kb = new Scanner(System.in);
		int menuChoice = 0;
		int menuChoice = 0;

		while (menuChoice < 9) {
			System.out.println("Choose one of the following:");
			System.out.println("\t1.\tBrowse mutual funds");
			System.out.println("\t2.\tSearch mutual funds by text");
			System.out.println("\t3.\tInvest");
			System.out.println("\t4.\tSell shares");
			System.out.println("\t5.\tBuy shares");
			System.out.println("\t6.\tInvest with conditions");
			System.out.println("\t7.\tChange allocation preferences");
			System.out.println("\t8.\tSee customer portfolio");
			System.out.println("\t9.\tExit");

			menuChoice = checkInput();
			if (menuChoice == -1) continue;

			if (menuChoice == 1) {
				System.out.println("Would you like to...");
				System.out.println("\t1.\tSee all mutual funds");
				System.out.println("\t2.\tSee mutual funds by category");
				System.out.println("\t3.\tSee mutual funds sorted by highest price for a specific date");
				System.out.println("\t4.\tSee mutual funds sorted alphabetically");

				menuChoice = checkInput();
				if (menuChoice == -1) continue;

				if (menuChoice == 1) {
					// print the entire mutual funds table
				} else if (menuChoice == 2) {
					System.out.println("Which category? Fixed, bonds, mixed, or stocks?");
					String category = kb.next();
					// put category as input into a procedure that prints all mutual funds
					// with that category
				} else if (menuChoice == 3) {
					System.out.print("Type in the date (dd/mm/yyyy): ");
					String date = kb.next();
					// put date as input into a procedure that prints all
					// MUTUALFUND natural join CLOSINGPRICE
					// with that c_date, ordered by price
				} else if (menuChoice == 4) {
					// print entire mutual funds table
					// ordered by name asc
				}
			}
			else if (menuChoice == 2) {
				System.out.println("Search for up to two words in the mutual funds, separated by a space: ");
				String keywords = kb.nextLine();
				String[] keywords_split = keywords.split(" ");
				String keyword1 = keywords_split[0];
				String keyword2 = keywords_split[1];

				// pass keyword1 and keyword2 as arguments to procedure 
				// and use % to search for them in description of mutual funds
			}
			else if (menuChoice == 3) {
				System.out.println("How much would you like to invest? ");
				float invest = kb.nextFloat();
			}
		}
	}

	public static void checkInput() {
		try {
			int input = kb.nextInt();

			if (input < 1) input = -1;

			return input;
		} catch (IllegalArgumentException iae) {
			System.out.println("Type in the number corresponding to your choice.");
			return -1;
		}
	}
}