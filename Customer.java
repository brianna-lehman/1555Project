public class Customer {
	String login
	String name
	String email
	String address
	float balance

	/** Constructor */
	public Customer(String login, String name, String email, String address, float balance) {
		this.login = login;
		this.name = name;
		this.email = email;
		this.address = address;
		this.balance = balance;
	}

	/** The user inputs a choice from a text menu, 
	 *	based on the choice, a mutual fund table is printed
	 */
	public void browse(int choice) {

		// prints the entire mutual fund table if the user choses 1 or types in a number that isn't an option
		if (choice <= 1 || choice > 4) {
			//** sql **//
			select *
			from MUTUALFUND;
			//** sql **//
		}

		// prints all the mutual funds of a specific category
		else if (choice == 2) {
			System.out.println("Which category? Fixed, bonds, mixed, or stocks?");
			String category = kb.next();

			//** sql **//
			// put category as input into a procedure that prints all mutual funds with that category
			call browse_mf_category(category);
			//** sql **//
		}

		// prints all the mutual funds that were created on a user specified day, ordered by price ascending
		else if (choice == 3) {
			System.out.print("Type in the date (dd/mm/yyyy): ");
			String date = kb.next();

			//** sql **//
			//** ?how to turn input into a date data type? **//
			call browse_mf_date(date);
			//** sql **//
		}

		// prints all the mutual funds in order by name ascending
		else if (choice == 4) {
			// ?how to call the browse_mf_name view?
		}
	}

	/** calls a procedure that searches descriptions of mutual funds for the user specified keywords 
	 *	?I don't think this prints to the screen?
	 */ 
	public void search(String key1, String key2) {
		//** sql **//
		call keyword_search(key1, key2);
		//** sql **//
	}

	public void invest(float amount) {

	}

	/** calls a procedure that returns the price of one share and the total price of all shares sold
	 *	this data gets inserted into the trxlog table which triggers an increase on customer's balance
	 *	and the customer object's balance is updated as well
	 */
	public void sell(String symbol, int shares) {
		float total_price;
		float price_of_one_share;

		//** sql **//
		call total_shares_price(symbol, shares, total_price, price_of_one_share);
		insert into TRXLOG values(trans_id++, login, symbol, ?date?, 'sell', shares, price_of_one_share, total_price);
		// this will trigger 'increase_customer_balance'
		//** sql **//

		// update the customer object balance field
		balance += total_price;
	}

	/** user can chose to buy mutual funds based on number of shares or price */ 
	public void buy(String symbol, int choice) {

		// buying based on shares
		if (choice == 1) {
			System.out.print("How many shares would you like to buy? ");
			int shares = kb.nextInt();
			float total_price;
			float price_of_one_share;

			//** sql **//
			// find the price of a single share of the mutual fund and the total price of all the shares
			call total_shares_price(symbol, shares, total_price, price_of_one_share);
			//** sql **//

			if (total_price > balance) {
				System.out.println("You don't have enough money to buy this amount of shares.");
			}
			else 
				balance -= total_price;

				//** sql **//
				// try to insert an entry into trxlog(trans_id, login, symbol, date, 'buy', shares, price, price*shares)
				insert into TRXLOG values(trans_id++, login, symbol, ?date?, 'buy', shares, price_of_one_share, total_price);
				// this will trigger 'decrease_customer_balance'
				//** sql **/
		}

		// buying based on price
		else if (choice == 2) {
			System.out.print("How much would you like to spend? ");
			float total_price = kb.nextFloat();
			int shares;
			float price_of_one_share;

			if total_price > balance
				System.out.println("You don't have enough money to buy this amount of shares.");
			else
				//** sql **//
				call num_shares_from_input_price(symbol, total_price, shares, price_of_one_share);
				insert into TRXLOG values(trans_id++, login, symbol, date, 'buy', shares, price_of_one_share, total_price);
				// this will trigger 'decrease_customer_balance'
				//** sql **//
		}

		// incorrect input
		else System.out.println("Your only options are 1 and 2");
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