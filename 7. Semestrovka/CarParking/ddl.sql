create extension if not exists "uuid-ossp";

create table users (
                       id uuid primary key default uuid_generate_v4(),
                       first_name varchar(20) not null,
                       last_name varchar(20) not null,
                       password varchar(30) not null,
                       email varchar(20),
                       birthdate date not null
);