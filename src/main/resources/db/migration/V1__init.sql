CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE topics
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_topic
(
    user_id  INTEGER NOT NULL,
    topic_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, topic_id)
);

CREATE TABLE trust_levels
(
    benefactor_id INTEGER NOT NULL,
    beneficiary_id INTEGER NOT NULL,
    level INTEGER NOT NULL,
    PRIMARY KEY (benefactor_id, beneficiary_id)
);

ALTER TABLE IF EXISTS user_topic
    ADD CONSTRAINT fk_user_topic_user FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_topic
    ADD CONSTRAINT fk_user_topic_topic FOREIGN KEY (topic_id) REFERENCES topics;

ALTER TABLE IF EXISTS trust_levels
    ADD CONSTRAINT fk_user_topic_from FOREIGN KEY (benefactor_id) REFERENCES users;

ALTER TABLE IF EXISTS trust_levels
    ADD CONSTRAINT fk_user_topic_to FOREIGN KEY (beneficiary_id) REFERENCES users;
