create database toby;
use toby;
create table users(
    id varchar(32) not null primary key,
    name varchar(32) not null,
    password varchar(32) not null
    );