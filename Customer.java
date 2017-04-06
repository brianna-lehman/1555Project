public class Customer {
	String login
	String name
	String email
	String address
	float balance

	public Customer(String login, String name, String email, String address, float balance) {
		this.login = login;
		this.name = name;
		this.email = email;
		this.address = address;
		this.balance = balance;
	}

	public void browse(int choice) {

		if (choice == 1) {
			// print entire mutual funds table
		} else if (choice == 2) {
			System.out.println("Which category? Fixed, bonds, mixed, or stocks?");
			String category = kb.next();
			// put category as input into a procedure that prints all mutual funds
			// with that category
		} else if (choice == 3) {
			System.out.print("Type in the date (dd/mm/yyyy): ");
			String date = kb.next();
			// put date as input into a procedure that prints all
			// MUTUALFUND natural join CLOSINGPRICE
			// with that c_date, ordered by price
		} else if (choice == 4) {
			// print entire mutual funds table
			// ordered by name asc
		} else // print some error message and exit
	}

	public void search(String key1, String key2) {
		// pass keyword1 and keyword2 as arguments to procedure 
		// and use % to search for them in description of mutual funds
	}

	public void invest(float amount) {

	}

	public void sell(String symbol, int shares) {
		// create a join table of MUTUALFUND and CLOSINGPRICE where symbol = input symbol
			// get value of price*shares
			// create a trigger where: 
				// before insert on CUSTOMER
				// when the balance is about to be changed
				// make balance += price*shares
				// insert new balance into table
	}

	public void buy(String symbol, int choice) {
		if (choice == 1) {
			System.out.print("How many shares would you like to buy? ");
			int shares = kb.nextInt();
		}
		else if (choice == 2) {
			System.out.print("How much would you like to spend? ");
			float price = kb.nextFloat();
		} else // print some error message and exit
	}
}