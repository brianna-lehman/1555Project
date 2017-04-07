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