SELECT
    cp.chat_id,
    cp2.user_id          AS partner_user_id,
    u.user_name          AS partner_user_name,
    u.picture_name       AS partner_picture_name,
    NULL                 AS last_message,
    NULL                 AS last_message_at,
    0                    AS unread_count
FROM chat_participants cp
JOIN chat_participants cp2
    ON cp.chat_id = cp2.chat_id AND cp2.user_id <> cp.user_id
JOIN users u
    ON u.user_id = cp2.user_id
WHERE cp.chat_id = /* chatId */'chat1'
LIMIT 1
