create table file_info
(
    id serial not null
        constraint file_info_pk
            primary key,
    original_file_name varchar(100),
    storage_file_name varchar(100) not null,
    size bigint not null,
    type varchar(100)
);

create table users
(
    id serial not null
        constraint users_pk
            primary key,
    first_name varchar(20),
    last_name varchar(20),
    age integer,
    password_hash varchar(100) not null,
    email varchar(100) not null unique,
    avatar_id integer
        constraint users_file_info_id_fk
            references file_info
);
