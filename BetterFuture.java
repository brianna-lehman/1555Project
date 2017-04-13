public class BetterFuture {

	public static Sanner kb = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("What type of user are you?");
		System.out.println("\t1.\tAdministrator\n\t2.\tCustomer");
		String user = kb.next();
		// checks if user is an administrator
		if (user.compareToIgnoreCase("administrator") == 0) {
			adminInterface();
		}
		// checks if user is a returning customer
		else if (user.compareToIgnoreCase("customer") == 0) {
			customerInterface();
		}
		else {
			System.out.println("Incorrect input.");
		}
	} // end main()

	// interface used for the administrator
	public static void adminInterface() {
		int menuChoice = 0;
		Admin admin = adminLogin();

		while (menuChoice < 5) {
			System.out.println("\nChoose one of the following:");
			System.out.println("\t1.\tNew user registration");
			System.out.println("\t2.\tUpdate share quotes for a day");
			System.out.println("\t3.\tAdd a new mutual fund");
			System.out.println("\t4.\tUpdate the time and date");
			System.out.println("\t5.\tView current statistics");
			System.out.println("\t6.\tExit");
			// determines input
			menuChoice = checkInput();
			if (menuChoice == -1) continue;
			// different menu options
			switch (menuChoice) {
				// NEW USER REGISTRATION
				case 1:
					boolean ifSuccess = false;
					System.out.println("Is the new user and administrator? (Y/N)");
					char isAdmin = kb.next();
					isAdmin = Character.toUpperCase(isAdmin);
					if (isAdmin == 'Y') {
						// insert new login into Admin db
						System.out.println("Please enter the following information:"):
						System.out.println("New Login: ");
						String adminLog = kb.next();
						// confirm login is okay
						while (admin.checkLogin(adminLog, true) != true) {
							System.out.println("This login already exists. Please enter a new one.");
							System.out.print("New Login: \n");
							adminLog = kb.next();
						} // end while
						System.out.print("Password: ");
						String adminPass = kb.next();
						System.out.println("Full Name: ");
						String adminName = kb.next();
						System.out.println("Email: ");
						String adminEmail = kb.next();
						System.out.println("Address: ");
						String adminAddress = kb.next();

						// embedded sql
						// insert function will be included here
						// INSERT INTO ADMINISTRATOR(adminLog, adminName, adminEmail,
						//													 adminAddress, adminPass);

						System.out.println("\nAdministrator data has been stored successfully!\n");
					}
					// If the new user is not an admin
					else {
						System.out.println("Please enter the following information:"):
						System.out.println("New Login: ");
						String customerLog = kb.next();
						// confirm login is okay
						while (admin.checkLogin(adminLog, false) != true) {
							System.out.println("This login already exists. Please enter a new one.");
							System.out.print("New Login: \n");
							customerLog = kb.next();
						} // end while
						System.out.print("Password: ");
						String customerPass = kb.next();
						System.out.println("Full Name: ");
						String customerName = kb.next();
						System.out.println("Email: ");
						String customerEmail = kb.next();
						System.out.println("Address: ");
						String customerAddress = kb.next();

						// embedded sql
						// insert function will be included here
						// INSERT INTO CUSTOMER(customerLog, customerName, customerEmail,
					  //											customerAddress, customerPass, NULL);

						System.out.println("\nCustomer data has been stored successfully!\n");
					}
					break;
				// UPDATE SHARE QUOTES FOR THE DAY
				case 2:
					String choice = "";
					System.out.println("Which mutual fund would you like to update the
															share quotes for?");
					System.out.println("\t1. Money Market\n\t2. Real-Estate\n\t3. Short-term Bonds\n\t
																4. Long-term Bonds\n\t5. Balance Bonds Stocks\n\t
																6. Social Responsibility Bonds Stocks\n\t7. General Stocks\n\t
																8. Aggressive Stocks\n\t9. International Market Stocks\n\n");
					int fundChoice = kb.next();
					// proceeds depending on mutual fund choice
					switch(fundChoice) {
						case 1:
								choice = "money-market";
								admin.updateShare(choice);
								break;
						case 2;
								choice = "real-estate";
								admin.updateShare(choice);
								break;
						case 3;
								choice = "short-term-bonds";
								admin.updateShare(choice);
								break;
						case 4;
								choice = "long-term-bonds";
								admin.updateShare(choice);
								break;
						case 5;
								choice = "balance-bonds-stocks";
								admin.updateShare(choice);
								break;
						case 6;
								choice = "social-responsibility-bonds-stocks";
								admin.updateShare(choice);
								break;
						case 7:
								choice = "general-stocks";
								admin.updateShare(choice);
								break;
						case 8:
								choice = "aggressive-stocks";
								admin.updateShare(choice);
								break;
						case 9:
								choice = "international-markets-stocks";
								admin.updateShare(choice);
								break;
						default:
								System.out.println("Error: Please try again");
					} // end switch
					break;
				// ADD A NEW MUTUAL FUND
				case 3:
					System.out.println("What mutual fund would you like to add? ");

					break;
				// UPDATE THE TIME AND DATE
				case 4:

					break;
				// VIEW CURRENT STATISTICS
				case 5:

					break;
				// EXIT
				case 6:
					// not sure if programming is ending or getting taken back to orignal
					// home menu
					// if exit program entirely
					System.out.println("Good-bye!");
					System.exit(0);
					break;
				default:
					System.out.println("Type in a number corresponding to your choice.");
			} // end switch
		} // end while
	} // end adminInterface()

	// interface used for customer
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
			// determines user input
			menuChoice = checkInput();
			if (menuChoice == -1) continue;
			// if user wants to browse mutual funds
			if (menuChoice == 1) {
				System.out.println("Would you like to...");
				System.out.println("\t1.\tSee all mutual funds");
				System.out.println("\t2.\tSee mutual funds by category");
				System.out.println("\t3.\tSee mutual funds sorted by highest price for a specific date");
				System.out.println("\t4.\tSee mutual funds sorted alphabetically");
				// retrieves user input
				menuChoice = checkInput();
				if (menuChoice == -1) continue;

				customer.browse(menuChoice);
			}
			// if user wants to search for mutual funds
			else if (menuChoice == 2) {
				System.out.print("Search for up to two words in the mutual funds, separated by a space: ");
				String keywords = kb.nextLine();
				String[] keywords_split = keywords.split(" ");
				String keyword1 = keywords_split[0];
				String keyword2 = keywords_split[1];

				customer.search(keyword1, keyword2);
			}
			// if user wants to invest
			else if (menuChoice == 3) {
				System.out.print("How much would you like to invest? ");
				float invest = kb.nextFloat();

				//** this doesn't do anything! **/
				customer.invest(invest);
			}
			// if user would like to sell shares
			else if (menuChoice == 4) {
				System.out.print("What is the mutual fund symbol? ");
				String symbol = kb.next();
				System.out.print("How many shares would you like to sell? ");
				int shares = kb.nextInt();

				customer.sell(symbol, shares);
			}
			// if user would like to buy shares
			else if (menuChoice == 5) {
				System.out.print("What is the mutual fund symbol? ");
				String symbol = kb.next();
				System.out.println("Would you like to buy by \n\t1.)\tnumber of shares \n\t2.)\tprice amount");
				menuChoice = checkInput();
				if (menuChoice == -1) continue;

				customer.buy(symbol, menuChoice);
			}
			// if user wants to invest with conditions
			else if (menuChoice == 6) {
				//** this doesn't do anything! **/
				customer.conditionInvest();
			}
			// if user wants to change allocation preferences
			else if (menuChoice == 7) {
				System.out.println("What is the allocation number of the preference you'd like to change? ");
				int number = kb.nextInt();
				customer.changePreference(number);
			}
			// if user wants to see there porfolio
			else if (menuChoice == 8) {
				System.out.print("Specify the date (yyyy-mm-dd)? ");
				String date = kb.next();

				customer.printPortfolio(String date);
			}
		} // end while
	} // end customerInterface()

	// checks for valid user input
	public static int checkInput() {
		try {
			int input = kb.nextInt();
			if (input < 1) input = -1;
			return input;
		} catch (IllegalArgumentException iae) {
			System.out.println("Type in the number corresponding to your choice.");
			return -1;
		}
	} // end checkInput()

	// Retrieves customer login information
	public static Customer customerLogin() {
		System.out.println("Login name: ");
		String login = kb.next();
		System.out.println("Password: ");
		String password = kb.next();
		String real_password;
		String name;
		String email;
		String address;
		String balance;

		//** embedded sql **//
		call check_login_customer(login, real_password, name, email, address, balance);
		//** embedded sql **//

		if (password.compareToIgnoreCase(real_password) != 0 || real_password == NULL) {
			System.out.println("The username or password is incorrect.");
			System.exit(1);
		}

		return new Customer(login, name, email, address, balance);

	} // end customerLogin()

	// Retrieves admin login information
	public static Admin adminLogin() {
		System.out.println("Login name: ");
		String login = kb.next();
		System.out.println("Password: ");
		String password = kb.next();
		String real_password;
		String name;
		String email;
		String address;

		//** embedded sql **//
		call check_login_admin(login, real_password, name, email, address);
		//** embedded sql **//

		if (password.compareToIgnoreCase(real_password) != 0 || real_password == NULL) {
			System.out.println("The username or password is incorrect or you are not authorized
													login in as an administrator.");
			System.exit(1);
		}

		return new Admin(login, name, email, address);
	} // end adminLogin()
}
