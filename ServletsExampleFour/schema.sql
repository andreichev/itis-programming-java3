create table file_info
(
    id serial not null primary key,
    original_file_name varchar(100),
    storage_file_name varchar(100) not null,
    size bigint not null,
    type varchar(100)
);

