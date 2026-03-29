-- =============================================================
-- OverSession DDL (テーブル定義)
-- Spring Boot 起動時に自動実行される
-- =============================================================

-- 既存テーブルを削除して再作成（開発環境用）
DROP TABLE IF EXISTS reactions CASCADE;
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS chat_participants CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id                    VARCHAR(64)  PRIMARY KEY,
    user_name                  VARCHAR(128) NOT NULL,
    primary_head_office_name   VARCHAR(128),
    secondary_head_office_name VARCHAR(128),
    department_name            VARCHAR(128),
    office_id                  INTEGER,
    floor                      INTEGER,
    gender                     VARCHAR(16),
    affiliation_year           INTEGER,
    working_status             VARCHAR(16)  NOT NULL DEFAULT '不在',
    matching_user_id           VARCHAR(64),
    picture_name               VARCHAR(256),
    deleted                    BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE chat_participants (
    chat_id  VARCHAR(64) NOT NULL,
    user_id  VARCHAR(64) NOT NULL,
    PRIMARY KEY (chat_id, user_id)
);

CREATE TABLE messages (
    message_id     VARCHAR(64)  PRIMARY KEY,
    chat_id        VARCHAR(64)  NOT NULL,
    sender_user_id VARCHAR(64)  NOT NULL,
    text           TEXT         NOT NULL,
    sent_at        TIMESTAMP    NOT NULL,
    read_at        TIMESTAMP
);

CREATE TABLE reactions (
    message_id    VARCHAR(64) NOT NULL,
    user_id       VARCHAR(64) NOT NULL,
    reaction_type INTEGER     NOT NULL,
    PRIMARY KEY (message_id, user_id, reaction_type)
);

-- インデックス
CREATE INDEX IF NOT EXISTS idx_messages_chat_id ON messages (chat_id, sent_at);
CREATE INDEX IF NOT EXISTS idx_reactions_message ON reactions (message_id);
CREATE INDEX IF NOT EXISTS idx_chat_participants ON chat_participants (user_id);
