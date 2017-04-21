-- THIS FILE IS JUST TO KEEP TRACK OF DATABASE THINGS THAT GET CREATED DURING MILESTONE 2
-- we'll put everything together later

-- returning all the information for a single administrator at login
create or replace procedure check_login_admin (in user_login varchar2(10),
						out password varchar2(10), out name varchar2(20),
						out email varchar2(30), out address varchar2(30))
	begin
		select c.password into password, c.name into name,
		c.email into email, c.address into address
		from ADMINISTRATOR c
		where login = user_login;
	end;
/

-- returns the symbol of the mutual funds this customer prefers to invest in
-- and the percentage of the user's investment that should be invested in this mutual fund
create or replace procedure specific_customer_preferences (in user_login varchar2(10))
	begin
		select symbol, percentage
		from customer_prefrences
		where login = user_login;
	end;
/

-- trigger to increase the customer balance when a 'sell' action is inserted into the trxlog table
create or replace trigger increase_customer_balance
	before insert on TRXLOG
	for each row
	when (new.action = 'sell')
	begin
		update CUSTOMER
		set balance = balance + :new.amount;
		where login = :new.login;
	end;
/

-- trigger to decrease the customer balance when a 'buy' action is inserted into the trxlog table
create or replace trigger decrease_customer_balance
	before insert on TRXLOG
	for each row
	when (new.action = 'buy')
	begin
		update CUSTOMER
		set balance = balance - :new.amount;
		where login = :new.login;
	end;
/

-- **************    DOES NOT WORK    *******************
-- Trigger set to insert buy transcations following the deposit insert into log
-- when a deposit is made, it is assumed that the deposit money is going towards
-- a buy to the customers mutual funds (the ones that preferenced)
create or replace trigger on_insert_log
	after insert on TRXLOG
	for each row
	when (new.action = 'deposit')
	begin
		-- the code below will be included into this trigger. this trigger is an 
		-- attempt of getting the information needed to update the shares bought
		-- using the preferences listed by the user in the mutual funds

		-- for every tuple in the table from specific_customer_preferences
			-- int amount_to_invest = amount * percentage
			-- call procedure num_shares_from_input_price(symbol, amount_to_invest, int num_shares, int share_price)
			-- insert into TRXLOG values(trans_id++, login, symbol, date, 'buy', num_shares, share_price, amount_to_invest);
		open specific_customer_preferences_c;
	end;
/

-- this cursor is used to get the different preferences and its distributions
declare
	cursor prefer_cursor is
		select allocation_no, symbol, percentage
		from ALLOCATION natural joins PREFERS;
 	prefer_record ALLOCATION%rowtype;
begin
	if not prefer_cursor%isopen
		then open prefer_cursor;
	end if;
	loop
		fetch prefer_cursor into prefer_rec;
		exit when prefer_record&notfound;
		-- this area will contain a function that deposits the money 
		-- based on the percentage of the fund
	end loop;
	close prefer_record;
end;
