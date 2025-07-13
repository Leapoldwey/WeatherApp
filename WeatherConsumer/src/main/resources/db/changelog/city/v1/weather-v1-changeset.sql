--liquibase formatted sql

--changeset Mihail:1.0.0
create table if not exists weather
(
    id          UUID primary key,
    city        varchar(64),
    temperature int,
    condition   varchar(64),
    time        timestamp
);