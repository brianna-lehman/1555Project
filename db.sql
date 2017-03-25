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

create domain mutfund_name as varchar(30)
	check (value in ('money-market', 'real-estate', 'short-termbonds', 'long-term-bonds'
					'balance-bonds-stocks', 'social-responsibility-bonds-stocks', 'general-stocks'
					'aggressive-stocks', 'international-markets-stocks'));

create table MUTUALFUND (
	symbol varchar2(20),
	-- constraint: only possible names are money-market, real-estate, short-termbonds,
	-- long-term-bonds, balance-bonds-stocks, social-responsibility-bonds-stocks, general-stocks,
	-- aggressive-stocks or international-markets-stocks
	name mutfund_name,
	description varchar2(100),
	-- constraint: only possible categories are fixed, bonds, stocks or mixed
	category varchar2(10)
	c_date date,
	constraint pk_mutualfund primary key(symbol)
);

-- create a trigger that adds an entry to closingprice every time an entry is added
-- to mutualfund
create table CLOSINGPRICE (
	symbol varchar2(20),
	price float(2),
	p_date date,
	constraint pk_closingprice primary key(symbol, p_date),
	constraint fk_closingprice_mutualfund foreign key(symbol)
		references MUTUALFUND(symbol)
);

create table CUSTOMER (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	balance float(2),
	constraint pk_customer primary key(login)
);

create table ADMINISTRATOR (
	login varchar2(10),
	name varchar2(20),
	email varchar2(30),
	address varchar2(30),
	password varchar2(10),
	constraint pk_administrator primary key(login)
);

create table ALLOCATION (
	allocation_no int,
	login varchar2(10),
	p_date date,
	constraint pk_allocation primary key(allocation_no),
	constraint fk_allocation_customer foreign key(login)
		references CUSTOMER(login)
);

create table PREFERS (
	allocation_no int,
	symbol varchar2(20),
	percentage float(2),
	constraint pk_prefers primary key(allocation_no, symbol),
	constraint fk_prefers_alloc foreign key(allocation_no)
		references ALLOCATION(allocation_no),
	constraint fk_prefers_mutfund foreign key(symbol)
		references MUTUALFUND(symbol)
);

create table TRXLOG (
	trans_id int,
	login varchar2(10),
	symbol varchar2(20),
	t_date date,
	-- create a constraint for action so that it's only 'deposit' 'sell' or 'buy'
	action varchar2(10),
	num_shares int,
	price float(2),
	amount float(2),
	constraint pk_trxlog primary key(trans_id),
	constraint fk_trxlog_cust foreign key(login)
		references CUSTOMER(login),
	constraint fk_trxlog_mutfund foreign key(symbol)
		references MUTUALFUND(symbol)
);

create table OWNS(
	login varchar2(10),
	symbol varchar(20),
	shares int,
	constraint pk_owns primary key(login, symbol),
	constraint fk_own_cust foreign key(login)
		references CUSTOMER(login),
	constraint fk_own_mutfund foreign key(symbol),
		references MUTUALFUND(symbol)
);

create table MUTUALDATE (
	c_date date,
	constraint pk_mutdate primary key(c_date)
);