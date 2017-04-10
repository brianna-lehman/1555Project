-- THIS FILE IS JUST TO KEEP TRACK OF DATABASE THINGS THAT GET CREATED DURING MILESTONE 2
-- we'll put everything together later

-- a view that gets used when you have to know the mutual fund and it's closing price
create or replace view mutualfund_price as
	select *
	from MUTUALFUND natural join CLOSINGPRICE;

-- returning all the information for a single customer at login
create or replace procedure check_login (in user_login varchar2(10), 
										out password varchar2(10), out name varchar2(20), 
										out email varchar2(30), out address varchar2(30), 
										out balance float(2))
	begin
		select c.password into password, c.name into name, 
		c.email into email, c.address into address,
		c.balance into balance 
		from CUSTOMER c
		where login = user_login;
	end;
	/

-- searches the mutual fund description for keywords
create or replace procedure keyword_search(in key1 varchar2(10), in key2 varchar2(10))
	begin
		select *
		from MUTUALFUND
		where description like %key1% or description like %key2%
	end;
	/

-- given the mutual fund symbol and number of shares looking to be bought
-- find the price of one of those shares and the total price of all the shares
create or replace procedure total_shares_price (in symbol varchar2(20), in num_shares int, 
												out total_price float(2), out single_price float(2))
	begin
		select mf.price*mf.shares into total_price, mf.price into single_price
		from mutualfund_price mf
		where mf.symbol = symbol
	end;
	/

-- given the mutual fund symbol and total price looking to be spent
-- find the price of one share and the maximum number of shares that can be bought for that price
create or replace procedure num_shares_from_input_price (in symbol varchar2(20), in total_price float(2),
														out num_shares int, out single_price float(2))
	begin
		select total_price/mf.price into num_shares, mf.price into single_price
		from mutualfund_price mf
		where mf.symbol = symbol
	end;
	/

-- returns the symbol of the mutual funds this customer prefers to invest in
-- and the percentage of the user's investment that should be invested in this mutual fund
create or replace procedure specific_customer_preferences (in user_login varchar2(10))
	begin
		select symbol, percentage
		from customer_prefrences
		where login = user_login
	end;
	/

-- prints the customer's profile information
create or replace procedure customer_profile (in today_date date, in user_login varchar2(10))
		begin
			select txl.symbol, txl.price, txl.num_shares, mf.price*trx.num_shares as current_value
			from TRXLOG txl natural join mutualfund_price mf
			where txl.t_date = today_date and txl.login = user_login

-- trigger to increase the customer balance when a 'sell' action is inserted into the trxlog table
create or replace trigger increase_customer_balance
	before insert on TRXLOG
	for each row
	when (new.action = 'sell')
	begin
		update CUSTOMER
		set balance = balance+total_price
		where login = user_login -- how to get this user_login information into the trigger?
	end;
/

-- trigger to decrease the customer balance when a 'buy' action is inserted into the trxlog table
create or replace trigger decrease_customer_balance
	before insert on TRXLOG
	for each row
	when (new.action = 'buy')
	begin
		update CUSTOMER
		set balance = balance - total_price
		where login = user_login -- how to get this user_login information into the trigger?
	end;
/