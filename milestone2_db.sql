-- THIS FILE IS JUST TO KEEP TRACK OF DATABASE THINGS THAT GET CREATED DURING MILESTONE 2
-- we'll put everything together later

create or replace view mutualfund_price as
	select *
	from MUTUALFUND natural join CLOSINGPRICE;

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

create or replace procedure keyword_search(in key1 varchar2(10), in key2 varchar2(10))
	begin
		select *
		from MUTUALFUND
		where description like %key1% or description like %key2%
	end;
	/

create or replace procedure total_shares_price (in symbol varchar2(20), in num_shares int, 
												out total_price float(2), out single_price float(2))
	begin
		select mf.price*mf.shares into total_price, mf.price into single_price
		from mutualfund_price mf
		where mf.symbol = symbol
	end;
	/

create or replace procedure num_shares_from_input_price (in symbol varchar2(20), in total_price float(2),
														out num_shares int, out single_price float(2))
	begin
		select total_price/mf.price into num_shares, mf.price into single_price
		from mutualfund_price mf
		where mf.symbol = symbol

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