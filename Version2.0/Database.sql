create table orders
(
contract_num varchar,
enterprise varchar,
product_model varchar,
quantity integer,
contract_manager integer not null,
contract_date date,
estimated_delivery_date date,
lodgement_date date,
salesman_num integer not null,
contract_type varchar(15),
id serial
);
create table product
(
id integer
unique,
supply_center varchar,
product_model varchar,
supply_staff integer,
date date,
purchase_price integer,
quantity integer
);
create table contract
(
contract_num varchar,
enterprise varchar,
contract_manager integer,
contract_date date,
estimated_delivery_date date,
lodgement_date date,
contract_type varchar
);
create table center
(
id int,
name varchar
);
create table enterprise(
id int,
name varchar,
country varchar,
city varchar,
supply_center varchar,
industry varchar
);
create table model(
id int,
number varchar,
model varchar,
name varchar,
unit_price int
);
create table staff(
id int,
name varchar,
age int,
gender varchar,
number int,
supply_center varchar,
mobile_number bigint,
type varchar
);