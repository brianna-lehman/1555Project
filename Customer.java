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

		// prints the entire mutual fund table if the user types in a number that isn't an option
		if (choice <= 1 || choice > 4) {
			//** sql **//
			select *
			from MUTUALFUND;
			//** sql **//
		}
		else if (choice == 2) {
			System.out.println("Which category? Fixed, bonds, mixed, or stocks?");
			String category = kb.next();

			// put category as input into a procedure that prints all mutual funds
			// with that category
			call browse_mf_category(category);
		}
		else if (choice == 3) {
			System.out.print("Type in the date (dd/mm/yyyy): ");
			String date = kb.next();
			// put date as input into a procedure that prints all
			// MUTUALFUND natural join CLOSINGPRICE
			// with that c_date, ordered by price

			//** how to turn input into a date data type? **//
			call browse_mf_date(date);
		}
		else if (choice == 4) {
			// print entire mutual funds table
			// ordered by name asc

			// call the browse_mf_name view
		}
	}

	public void search(String key1, String key2) {
		call keyword_search(key1, key2);
	}

	public void invest(float amount) {

	}

	public void sell(String symbol, int shares) {
		// create a join table of MUTUALFUND and CLOSINGPRICE where symbol = input symbol
			// get value of price*shares
			// try to insert entry into trxlog
			// create a trigger where: 
				// before insert on trxlog
				// when action == sell
				// make balance += price*shares in the customer table
	}

	public void buy(String symbol, int choice) {
		if (choice == 1) {
			System.out.print("How many shares would you like to buy? ");
			int shares = kb.nextInt();
			// find the price of the mutual fund, based on the symbol
			// if price*shares > customer.balance
				// print error - the customer doesn't have enough money to buy these shares
			// else 
				// try to insert an entry into trxlog(trans_id, login, symbol, date, 'buy', shares, price, price*shares)
				// create a trigger where:
					// before insert on trxlog
					// when action == buy
					// make balance -= price*shares in the customer table
		}
		else if (choice == 2) {
			System.out.print("How much would you like to spend? ");
			float price = kb.nextFloat();
			// if price > customer.balance
				// print error - the customer doesn't have enough money
			// else
				// calculate customer.balance/price - this is the number of shares they're buying
				// try to insert an entry into trxlog(trans_id, login, symbol, date, 'buy, balance/price, price, amount)
				// same sort of trigger as above (decrementing customer balance)
		}
		else // print some error message and exit
	}

	public void conditionInvest() {

	}

	public void changePreference() {

	}

	public void printPortfolio(String date) {
		// 	select trx.symbol, trx.price, trx.num_shares, mf.price*trx.num_shares as current_value
		//	from TRXLOG txl natural join mutualfund_price mf
		//	where txl.t_date = date and txl.login = customer.login
	}
}