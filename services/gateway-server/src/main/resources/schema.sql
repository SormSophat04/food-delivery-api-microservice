create sequence if not exists api_routes_id_seq start with 1;

create table if not exists api_routes (
    id bigint primary key default nextval('api_routes_id_seq'),
    uri varchar(255) not null,
    path varchar(255) not null ,
    method varchar(255) not null ,
    description varchar(255),
    group_code varchar(50),
    rate_limit int default 0,
    rate_limit_duration int default 0,
    status varchar(25) default 'ACTIVE',
    create_at timestamp default current_timestamp,
    create_by varchar(255),
    update_at timestamp default current_timestamp,
    update_by varchar(255)
);