-- =============================================================
-- OverSession 初期ダミーデータ
-- Spring Boot 起動時に schema.sql の後に自動実行される
-- =============================================================

-- ユーザ
INSERT INTO users (user_id, user_name, deleted) VALUES
    ('user01', '田中 太郎', FALSE),
    ('user02', '鈴木 花子', FALSE),
    ('user03', '山田 健一', FALSE)
ON CONFLICT DO NOTHING;

-- チャット参加者
INSERT INTO chat_participants (chat_id, user_id) VALUES
    ('chat01', 'user01'),
    ('chat01', 'user02'),
    ('chat02', 'user01'),
    ('chat02', 'user03')
ON CONFLICT DO NOTHING;

-- メッセージ・リアクションは DynamoDB に移行済み
-- シードデータは DynamoDbTableInitializer が投入する
