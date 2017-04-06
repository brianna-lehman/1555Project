public class BetterFuture {

	public static Sanner kb = new Scanner(System.in);
	
	public static void main(String[] args) {
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
		int menuChoice = 0;

		Customer customer = customerLogin();

		while (menuChoice < 9) {
			System.out.println("\nChoose one of the following:");
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

				customer.browse(menuChoice);
			}
			else if (menuChoice == 2) {
				System.out.print("Search for up to two words in the mutual funds, separated by a space: ");
				String keywords = kb.nextLine();
				String[] keywords_split = keywords.split(" ");
				String keyword1 = keywords_split[0];
				String keyword2 = keywords_split[1];

				customer.search(keyword1, keyword2);
			}
			else if (menuChoice == 3) {
				System.out.print("How much would you like to invest? ");
				float invest = kb.nextFloat();

				customer.invest(invest);
			}
			else if (menuChoice == 4) {
				System.out.print("What is the mutual fund symbol? ");
				String symbol = kb.next();
				System.out.print("How many shares would you like to sell? ");
				int shares = kb.nextInt();

				customer.sell(symbol, shares);
			}
			else if (menuChoice == 5) {
				System.out.print("What is the mutual fund symbol? ");
				String symbol = kb.next();
				System.out.println("Would you like to buy by \n\t1.)\tnumber of shares \n\t2.)\tprice amount");
				menuChoice = checkInput();
				if (menuChoice == -1) continue;

				if (menuChoice == 1) {
					System.out.print("How many shares would you like to buy? ");
					int shares = kb.nextInt();
				}
				else if (menuChoice == 2) {
					System.out.print("How much would you like to spend? ");
					float price = kb.nextFloat();
				}
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

	public static Customer login() {
		System.out.println("Login name: ");
		String login = kb.next();
		System.out.println("Password: ");
		String password = kb.next();

		// find tuple in CUSTOMER where CUSTOMER.login = login
		// if CUSTOMER.password != password || tuple == null
			// System.out.println("Incorrect input.");
			// System.exit(1);

		return new Customer(login, name, email, address, balance);

	}
}