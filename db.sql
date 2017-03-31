-- ***********************************************************************************************************
--  NOTES:
--  - Ask if views are needed in the first phase. If so, where would we need them
--  - Ask about contraint needed in ALLOCATION for 30 day requirement. How do you approach it?
--  - Where do we need to use functions and/or procedures
-- ************************************************************************************************************


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

create table MUTUALFUND (
	symbol varchar2(20),
	name varchar2(30),
	description varchar2(100),
	category varchar2(10),
	c_date date,
	constraint mutfund_name check (name in ('money-market', 'real-estate', 'short-termbonds', 'long-term-bonds',
					'balance-bonds-stocks', 'social-responsibility-bonds-stocks', 'general-stocks',
					'aggressive-stocks', 'international-markets-stocks')),
	constraint category_check check (category in ('fixed', 'bonds', 'stocks', 'mixed')),
	constraint pk_mutualfund primary key(symbol) deferrable initially immediate
);

create table CLOSINGPRICE (
	symbol varchar2(20),
	price float(2),
	p_date date,
	constraint pk_closingprice primary key(symbol, p_date) deferrable initially immediate,
	constraint fk_closingprice_mutualfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

create table CUSTOMER (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	balance float(2),
	constraint pk_customer primary key(login) deferrable initially immediate
);

create table ADMINISTRATOR (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	constraint pk_administrator primary key(login) deferrable initially immediate
);

create table ALLOCATION (
	allocation_no int,
	login varchar2(10),
	p_date date,
	constraint pk_allocation primary key(allocation_no) deferrable initially immediate,
	constraint fk_allocation_customer foreign key(login)
		references CUSTOMER(login) deferrable initially immediate
);

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

create table TRXLOG (
	trans_id int,
	login varchar2(10),
	symbol varchar2(20),
	t_date date,
	action varchar2(10),
	num_shares int,
	price float(2),
	amount float(2),
	constraint trx_action check (action in ('deposit', 'sell', 'buy')), 
	constraint pk_trxlog primary key(trans_id) deferrable initially immediate,
	constraint fk_trxlog_cust foreign key(login)
		references CUSTOMER(login) deferrable initially immediate,
	constraint fk_trxlog_mutfund foreign key(symbol)
		references MUTUALFUND(symbol) deferrable initially immediate
);

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

create table MUTUALDATE (
	c_date date,
	constraint pk_mutdate primary key(c_date) deferrable initially immediate
);

create view browse_mf_name as
	select *
	from MUTUALFUND mf
	group by mf.name asc;

create view all_customer_data as
	select o.shares, mf.symbol, mf.name, mf.description, 
			mf.category, mf.c_date, cp.price, cp.p_date
	from CUSTOMER c NATURAL JOIN 
		 OWNS o NATURAL JOIN
		 MUTUALFUND mf NATURAL JOIN 
		 CLOSINGPRICE cp;

create view customer_prefrences as 
	select *
	from CUSTOMER c NATURAL JOIN
		 ALLOCATION a NATURAL JOIN
		 PREFERS p;

create or replace procedure browse_mf_category (in category_var varchar(10))
	begin
		select *
		from MUTUALFUND
		where category = category_var;
	end;
	/

create or replace procedure browse_mf_category (in date_var date)
	begin
		select mf.symbol, mf.name, mf.description, mf.category, mf.c_date
		from MUTUALFUND mf NATURAL JOIN CLOSINGPRICE cp
		where mf.c_date = date_var
		group by cp.price asc;
	end;
	/

-- write something that adds up all the % in prefers so that they == 0
	
-- TRIGGERS
create or replace trigger on_insert_allocation
	before insert on ALLOCATION
	for each row
	when (new.p_date >= DATEADD(d, -30, GETDATE()))
	begin insert into ALLOCATION values (:new.allocation_no, :new.p_date);
	end;
	/

-- **************    CHECK    *******************
create or replace trigger on_insert_log
	after insert on TRXLOG
	for each row
	when (new.action = 'deposit')
	begin -- I DONT KNOW WHAT GOES HERE
	end;
/

commit;
