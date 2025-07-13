--liquibase formatted sql

--changeset Mihail:1.0.0
create table if not exists city
(
    id   UUID primary key,
    name varchar(64) unique
);

--changeset Mihail:1.0.1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset Mihail:1.0.2
insert into city(id, name)
values (uuid_generate_v4(), 'Москва'),
       (uuid_generate_v4(), 'Екатеринбург'),
       (uuid_generate_v4(), 'Омск'),
       (uuid_generate_v4(), 'Самара'),
       (uuid_generate_v4(), 'Пенза'),
       (uuid_generate_v4(), 'Тюмень');