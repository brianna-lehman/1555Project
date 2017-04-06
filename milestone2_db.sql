-- THIS FILE IS JUST TO KEEP TRACK OF DATABASE THINGS THAT GET CREATED DURING MILESTONE 2
-- we'll put everything together later

create or replace view mutualfund_price as
	select *
	from MUTUALFUND natural join CLOSINGPRICE;