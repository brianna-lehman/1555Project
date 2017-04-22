insert into MUTUALDATE values ('04-01-14');

insert into CUSTOMER values ('mike', 'Mike', 'mike@betterfuture.com', '1st street', 'pwd', 750);
insert into CUSTOMER values ('mary', 'Mary', 'mary@betterfuture.com', '2st street', 'pwd', 0);

insert into ADMINISTRATOR values ('admin', 'Administrator', 'admin@betterfuture.com', '5th Ave, Pitt', 'root');

insert into MUTUALFUND values ('MM', 'money-market', 'money market, conservative', 'fixed', '06-01-14');
insert into MUTUALFUND values ('RE', 'real-estate', 'real estate', 'fixed', '09-01-14');
insert into MUTUALFUND values ('STB', 'short-term-bonds', 'short term bonds', 'bonds', '10-01-14');
insert into MUTUALFUND values ('LTB', 'long-term-bonds', 'long term bonds', 'bonds', '11-01-14');
insert into MUTUALFUND values ('BBS', 'balance-bonds-stocks', 'balanced bonds and stocks', 'mixed', '12-01-14');	
insert into MUTUALFUND values ('SRBS', 'social-responsibility-bonds-stocks', 'social responsibility bonds and stocks', 'mixed', '12-01-14');
insert into MUTUALFUND values ('GS', 'general-stocks', 'general stocks', 'stocks', '16-01-14');
insert into MUTUALFUND values ('AS', 'aggressive-stocks', 'aggressive stocks', 'stocks', '23-01-14');
insert into MUTUALFUND values ('IMS', 'international-markets-stock', 'international markets stock, risky', 'stocks', '30-01-14');

insert into OWNS values ('mike', 'RE', '50');

insert into TRXLOG(trans_id, login, t_date, action, amount) values (0, 'mike', '29-01-14', 'deposit', 1000);
insert into TRXLOG values (1, 'mike', 'MM', '29-MAR-14', 'buy', 50, 10, 500);
insert into TRXLOG values (2, 'mike', 'RE', '29-MAR-14', 'buy', 50, 10, 500);
insert into TRXLOG values (3, 'mike', 'MM', '01-APR-14', 'sell', 50, 15, 750);

insert into ALLOCATION values (0, 'mike', '28-01-14');
insert into ALLOCATION values (1, 'mary', '29-01-14');
insert into ALLOCATION values (2, 'mike', '03-01-14');

insert into PREFERS values (0, 'MM', .5);
insert into PREFERS values (0, 'RE', .5);
insert into PREFERS values (1, 'STB', .2);
insert into PREFERS values (1, 'LTB', .4);
insert into PREFERS values (1, 'BBS', .4);
insert into PREFERS values (2, 'GS', .3);
insert into PREFERS values (2, 'AS', .3);
insert into PREFERS values (2, 'IMS', .4);

insert into CLOSINGPRICE values ('MM', 10, '28-01-14');

commit;