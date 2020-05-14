insert  into transaction (id, type, amount, open_price, uuid, stock_id, user_id, active, createddate)
values(1, 'BUY', 5000, 123.4, random_uuid(), 1, 1, true, current_timestamp());

insert  into transaction (id, type, amount, open_price, uuid, stock_id, user_id, active, createddate)
values(2, 'BUY', 2000, 123.4, random_uuid(), 2, 1, true, current_timestamp());

insert  into transaction (id, type, amount, open_price, uuid, stock_id, user_id, active, createddate)
values(3, 'BUY', 10000, 353.4, random_uuid(), 2, 1, true, current_timestamp());
