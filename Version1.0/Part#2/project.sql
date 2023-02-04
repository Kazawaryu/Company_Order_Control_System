create table product
(
    product_id    serial
        primary key,
    product_code  varchar not null
        unique,
    product_model varchar not null,
    product_name  varchar not null,
    price         integer not null
);

alter table product
    owner to postgres;

create index product_index
    on product (product_id);

create table supplycenter
(
    supply_center_id serial
        primary key,
    supply_center    varchar not null
        unique,
    manager          varchar not null
);

alter table supplycenter
    owner to postgres;

create index supplycenter_index
    on supplycenter (supply_center_id);

create table relation
(
    industry_id      serial
        primary key,
    industry         varchar not null
        unique,
    supply_center_id integer
        references supplycenter
);

alter table relation
    owner to postgres;

create index relation_index
    on relation (industry_id);

create table customer
(
    customer_id            serial
        primary key,
    client_enterprise_name varchar not null
        unique,
    country                varchar not null,
    city                   varchar,
    industry_id            integer not null
        references relation
);

alter table customer
    owner to postgres;

create index customer_index
    on customer (customer_id);

create table salesman
(
    salesman_id      serial
        primary key,
    salesman_number  integer not null
        unique,
    salesman_name    varchar not null,
    gender           varchar not null,
    age              integer not null,
    phone_number     bigint  not null,
    supply_center_id integer
        references supplycenter
);

alter table salesman
    owner to postgres;

create index salesman_index
    on salesman (salesman_id);

create table contract
(
    contract_id     serial
        primary key,
    contract_number varchar not null
        unique,
    contract_date   date    not null,
    customer_id     integer
        references customer
);

alter table contract
    owner to postgres;

create index contract_index
    on contract (contract_id);

create table orders
(
    order_id                serial
        primary key,
    contract_number         integer
        references contract,
    product_code            integer
        references product,
    salesman_number         integer
        references salesman,
    product_num             integer not null,
    estimated_delivery_date date    not null,
    lodgement_date          date
);

alter table orders
    owner to postgres;

create index orders_index
    on orders (order_id);


