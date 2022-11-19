create schema if not exists products;

create table if not exists products.sizes (
    id varchar(100) not null,
    description varchar(100) not null, 
    short_description varchar(10) not null,
    size_range varchar(50) not null,
    primary key (id)
);