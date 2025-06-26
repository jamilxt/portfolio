CREATE TABLE IF NOT EXISTS post_en (
                         id BIGINT PRIMARY KEY,
                         title VARCHAR(255),
                         content CLOB,
                         author VARCHAR(255),
                         created_at TIMESTAMP
);

