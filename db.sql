
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

-- REDO CHECKSSSS
-- Table listing the information of a variety of mutual funds, categorzied appropriately
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
create view browse_mf_name as
	select *
	from MUTUALFUND mf
	group by mf.name asc;

-- Temporarily lists the current mutual funds of the customer
create view all_customer_data as
	select o.shares, mf.symbol, mf.name, mf.description, 
			mf.category, mf.c_date, cp.price, cp.p_date
	from CUSTOMER c NATURAL JOIN 
		 OWNS o NATURAL JOIN
		 MUTUALFUND mf NATURAL JOIN 
		 CLOSINGPRICE cp;

-- Temporarily lists all the information from the CUSTOMER relation and ALLOCATION relation
-- without duplicate information
create view customer_prefrences as 
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
create or replace procedure browse_mf_category (in date_var date)
	begin
		select mf.symbol, mf.name, mf.description, mf.category, mf.c_date
		from MUTUALFUND mf NATURAL JOIN CLOSINGPRICE cp
		where mf.c_date = date_var
		group by cp.price asc;
	end;
	/

-- write something that adds up all the % in prefers so that they == 0
-- Hey Bri, make sure that a check of the distribution percentages == 100%
-- after a preference change is made (maybe use a procedure?)
-- just so you know I'm still do the 30 day check
	
-- Turn this into a procedure (make sure to list assumption that 30 day check was used insteaad of one month)
create or replace trigger on_insert_allocation
	before insert on ALLOCATION
	for each row
	when (new.p_date >= DATEADD(d, -30, GETDATE()))
	begin insert into ALLOCATION values (:new.allocation_no, :new.p_date);
	end;
	/

-- **************    CHECK    *******************
-- Trigger set to insert buy transcations following the deposit insert into log
-- when a deposit is made, it is assumed that the deposit money is going towards
-- a buy to the customers mutual funds (the ones that preferenced)
create or replace trigger on_insert_log
	after insert on TRXLOG
	for each row
	when (new.action = 'deposit')
	begin -- I DONT KNOW WHAT GOES HERE
	end;
/

commit;
