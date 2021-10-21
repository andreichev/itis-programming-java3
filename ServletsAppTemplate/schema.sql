create table file_info
(
    id serial not null primary key,
    original_file_name varchar(100),
    storage_file_name varchar(100) not null,
    size bigint not null,
    type varchar(100)
);

create table users
(
    id serial not null primary key,
    first_name varchar(20),
    last_name varchar(20),
    age integer,
    password_hash varchar(100) not null,
    email varchar(100) not null unique,
    avatar_id integer references file_info
);

create table posts
(
    id serial primary key,
    author_id int not null references users,
    created_at timestamp not null,
    content varchar(1000) not null
);