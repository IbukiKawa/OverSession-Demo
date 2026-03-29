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

-- メッセージ (NOW() ベースで相対時刻を設定)
INSERT INTO messages (message_id, chat_id, sender_user_id, text, sent_at, read_at) VALUES
    -- chat01
    ('msg001', 'chat01', 'user01', 'こんにちは！マッチングしましたね。',   NOW() - INTERVAL '30 minutes', NOW() - INTERVAL '28 minutes'),
    ('msg002', 'chat01', 'user02', 'こんにちは！よろしくお願いします。',   NOW() - INTERVAL '25 minutes', NOW() - INTERVAL '24 minutes'),
    ('msg003', 'chat01', 'user01', '今日ランチでもどうですか？',           NOW() - INTERVAL '20 minutes', NOW() - INTERVAL '18 minutes'),
    ('msg004', 'chat01', 'user02', 'いいですね！12時でどうでしょう？',     NOW() - INTERVAL '15 minutes', NOW() - INTERVAL '14 minutes'),
    ('msg005', 'chat01', 'user01', '12時で大丈夫です。',                   NOW() - INTERVAL '12 minutes', NOW() - INTERVAL '11 minutes'),
    ('msg006', 'chat01', 'user02', 'よろしくお願いします！',               NOW() - INTERVAL '10 minutes', NULL),
    ('msg007', 'chat01', 'user02', '明日もよろしくお願いします！',         NOW() - INTERVAL '9 minutes',  NULL),
    -- chat02
    ('msg008', 'chat02', 'user03', 'はじめまして！',                       NOW() - INTERVAL '48 hours',   NOW() - INTERVAL '47 hours 30 minutes'),
    ('msg009', 'chat02', 'user01', 'はじめまして！よろしくお願いします。', NOW() - INTERVAL '47 hours',   NOW() - INTERVAL '46 hours'),
    ('msg010', 'chat02', 'user03', 'ありがとうございました',               NOW() - INTERVAL '24 hours',   NOW() - INTERVAL '23 hours 30 minutes')
ON CONFLICT DO NOTHING;

-- リアクション (msg003 に user02 が type=1)
INSERT INTO reactions (message_id, user_id, reaction_type) VALUES
    ('msg003', 'user02', 1)
ON CONFLICT DO NOTHING;
