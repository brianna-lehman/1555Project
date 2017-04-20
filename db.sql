
drop table MUTUALFUND cascade constraints;
drop table CLOSINGPRICE cascade constraints;
drop table CUSTOMER cascade constraints;
drop table ADMINISTRATOR cascade constraints;
drop table ALLOCATION cascade constraints;
drop table PREFERS cascade constraints;
drop table TRXLOG cascade constraints;
drop table OWNS cascade constraints;
drop table MUTUALDATE cascade constraints;

purge recyclebin;

-- Table listing the information of a variety of mutual funds, categorized appropriately
create table MUTUALFUND (
	symbol varchar2(20),
	name varchar2(30),
	description varchar2(100),
	category varchar2(10),
	c_date date,
	constraint mutfund_name check (name in ('money-market', 'real-estate') and category = 'fixed'
					or name in ('short-term-bonds', 'long-term-bonds') and category = 'bonds'
					or name in ('balance-bonds-stocks', 'social-responsibility-bonds-stocks') and category = 'mixed'
					or name in ('general-stocks', 'aggressive-stocks', 'international-markets-stocks') and category = 'stocks'),
	constraint pk_mutualfund primary key(symbol) deferrable initially immediate
);

-- Tracks the closing prices of the mutual funds
create table CLOSINGPRICE (
	symbol varchar2(20),
	price float(2),
	p_date date,
	constraint pk_closingprice primary key(symbol, p_date) deferrable initially immediate,
	constraint fk_closingprice_mutualfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

-- Stores unique customer information
create table CUSTOMER (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	balance float(2),
	constraint pk_customer primary key(login) deferrable initially immediate
);

-- Stores unique administrator information
create table ADMINISTRATOR (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	constraint pk_administrator primary key(login) deferrable initially immediate
);

-- Holds allocation information
create table ALLOCATION (
	allocation_no int,
	login varchar2(10),
	p_date date,
	constraint pk_allocation primary key(allocation_no) deferrable initially immediate,
	constraint fk_allocation_customer foreign key(login)
		references CUSTOMER(login) deferrable initially immediate
);

-- Holds the current preferences of a user
-- Update: there will be a check in Milestone 2 making sure that it has been 30 days
-- (or a month) to update the preferenes, but also so that the preference changes
-- with the distributions equal 100%
create table PREFERS (
	allocation_no int,
	symbol varchar2(20),
	percentage float(2),
	constraint pk_prefers primary key(allocation_no, symbol) deferrable initially immediate,
	constraint fk_prefers_alloc foreign key(allocation_no)
		references ALLOCATION(allocation_no) deferrable initially immediate,
	constraint fk_prefers_mutfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

-- log used to track every transaction done under an account
create table TRXLOG (
	trans_id int,
	login varchar2(10),
	symbol varchar2(20),
	t_date date,
	action varchar2(10),
	num_shares int,
	price float(2),
	amount float(2),
	-- Makes a check to make sure the action is one of the three listed: deposit, sell, buy
	constraint trx_action check (action in ('deposit', 'sell', 'buy')), 
	constraint pk_trxlog primary key(trans_id) deferrable initially immediate,
	constraint fk_trxlog_cust foreign key(login)
		references CUSTOMER(login) deferrable initially immediate,
	constraint fk_trxlog_mutfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

-- Information of the mutual funds owned by a user
create table OWNS(
	login varchar2(10),
	symbol varchar(20),
	shares int,
	constraint pk_owns primary key(login, symbol) deferrable initially immediate,
	constraint fk_own_cust foreign key(login)
		references CUSTOMER(login) deferrable initially immediate,
	constraint fk_own_mutfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

-- Holds the date
create table MUTUALDATE (
	c_date date,
	constraint pk_mutdate primary key(c_date) deferrable initially immediate
);

-- Temporarily lists all the mutual funds by name
create or replace view browse_mf_name as
	select *
	from MUTUALFUND mf
	order by mf.name asc;

-- Temporarily lists the current mutual funds of the customer
create or replace view all_customer_data as
	select shares, symbol, name, description, 
			category, c_date, price, p_date
	from CUSTOMER NATURAL JOIN 
		 OWNS NATURAL JOIN
		 MUTUALFUND NATURAL JOIN 
		 CLOSINGPRICE;

-- Temporarily lists all the information from the CUSTOMER relation and ALLOCATION relation
-- without duplicate information
create or replace view customer_prefrences as 
	select *
	from CUSTOMER c NATURAL JOIN
		 ALLOCATION a NATURAL JOIN
		 PREFERS p;
		 
-- Procedure used to see all the mutual funds based in a category based on the user's input
create or replace procedure browse_mf_category (in category_var varchar(10))
	begin
		select *
		from MUTUALFUND
		where category = category_var;
	end;
	/

-- Procedure used to see all the mutual funds created on a user specified date,
-- ordered by their current price
create or replace procedure browse_mf_date (in date_var date)
	begin
		select mf.symbol, mf.name, mf.description, mf.category, mf.c_date
		from mutualfund_price mf
		where mf.c_date = date_var
		group by cp.price asc;
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
	
commit;
