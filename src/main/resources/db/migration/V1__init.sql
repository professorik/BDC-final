CREATE TABLE users
(
    id       serial primary key ,
    name     varchar(255) unique
);

CREATE TABLE topics
(
    id       serial primary key,
    name     varchar(255) unique
);

CREATE TABLE trust_levels
(
    id serial primary key,
    benefactor_id serial,
    beneficiary_id serial
);
