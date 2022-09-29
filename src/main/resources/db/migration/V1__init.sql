CREATE TABLE users
(
    id       serial primary key ,
    name     varchar(255) unique NOT NULL
);

CREATE TABLE topics
(
    id       serial primary key,
    name     varchar(255) unique NOT NULL
);

CREATE TABLE user_topic
(
    id       serial primary key,
    user_id  serial NOT NULL ,
    topic_id serial NOT NULL
);

CREATE TABLE trust_levels
(
    id serial primary key,
    benefactor_id serial NOT NULL ,
    beneficiary_id serial NOT NULL ,
    level integer NOT NULL
);
