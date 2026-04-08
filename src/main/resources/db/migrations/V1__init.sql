CREATE TABLE bot_users (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT UNIQUE NOT NULL,
    nick_name VARCHAR(50),
    user_role VARCHAR(15)
);

CREATE TABLE Quest (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    first_node_id BIGINT
);

CREATE TABLE quest_node(
    id BIGSERIAL PRIMARY KEY,
    quest_id BIGINT,
    next_node BIGINT
);