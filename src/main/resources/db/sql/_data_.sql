create table user(id int primary key, uuid uuid, username varchar, passwordhash varchar, createddate timestamp);

insert  into USER(id, uuid, username, passwordhash, createddate)
values(1, random_uuid(), 'test', '$2a$10$ZPCOIoqc5uDas.O/C5tkd./QMOq42NLPA/Me9f/6UV7/.3dNMH8ju', current_timestamp());

select * from USER;


create table transaction (id int primary key, type varchar, amount decimal, open_price decimal, uuid uuid, stock int, user int, createddate timestamp)

insert  into transaction (id, type, amount, open_price, uuid,  stock_id, user_id,  createddate)
values(1, 'BUY', 5000, 123.4, random_uuid(), 1, 1, current_timestamp());

insert  into transaction (id, type, amount, open_price, uuid,  stock_id, user_id,  createddate)
values(2, 'BUY', 2000, 123.4, random_uuid(), 2, 1, current_timestamp());

select * from transaction;