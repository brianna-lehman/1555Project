import java.util.*;
import java.sql.*;

public class Customer {
	private static String login
	private static String name
	private static String email
	private static String address
	private static float balance
	// int trans_id;
	public static Scanner kb = new Scanner(System.in);
	private Connection connection;
	private Statement statement;
	private ResultSet res;
	private String query;

	/** Constructor */
	public Customer(String login, String name, String email, String address, float balance) {
		this.login = login;
		this.name = name;
		this.email = email;
		this.address = address;
		this.balance = balance;
		// trans_id = /*select MAX(trans_id) from TRXLOG*/+1;

		try {
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			connection = DriverManager.getConnection(url, "bml49", "3985224");
		}
		catch(Exception ex) {
			System.out.println("Error connecting to database.");
			ex.printStackTrace();
		}

	}

	/** The user inputs a choice from a text menu, 
	 *	based on the choice, a mutual fund table is printed
	 */
	public void browse(int choice) {

		// prints the entire mutual fund table if the user choses 1 or types in a number that isn't an option
		if (choice <= 1 || choice > 4) {
			PreparedStatement ps = connection.PreparedStatement("select * from MUTUALFUND");
			res = ps.executeQuery()
			System.out.println("Symbol\tName\tDescription\tCategory");

			while (res.next()) {
				System.out.print(res.getString("symbol")+"\t");
				System.out.print(res.getString("name")+"\t");
				System.out.print(res.getString("description")+"\t");
				System.out.print(res.getString("category")+"\t");
			}
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

	// a certain percentage of the amount is invested in buying a specific mutual fund
	// this doesn't check to make sure all the possible mutual funds can be bought 
	public void invest(float total_amount) {
		//** sql **//
		insert into TRXLOG(trans_id, login, t_date, action) values(trans_id++, login, date, 'deposit');
		// this triggers 'on_insert_log'
		//** sql **//

		balance -= total_amount;
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
			// finds the price of a single share of the mutual fund and the total price of all the shares
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

	public void changePreference(int alloc_number) {

	}

	/** Calls a procedure that prints all transactions that this user has implemented */
	public void printPortfolio(String date) {
		call customer_profile(?date?, login);
	}
}