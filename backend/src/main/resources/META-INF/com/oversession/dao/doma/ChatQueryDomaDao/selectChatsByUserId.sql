SELECT
    cp.chat_id,
    partner.user_id        AS partner_user_id,
    partner.user_name      AS partner_user_name,
    partner.picture_name   AS partner_picture_name,
    last_msg.text          AS last_message,
    last_msg.sent_at       AS last_message_at,
    COALESCE(unread.cnt, 0) AS unread_count
FROM chat_participants cp
JOIN chat_participants cp2
    ON cp.chat_id = cp2.chat_id AND cp2.user_id <> /* userId */'user1'
JOIN users partner
    ON partner.user_id = cp2.user_id
LEFT JOIN LATERAL (
    SELECT text, sent_at
    FROM messages
    WHERE chat_id = cp.chat_id
    ORDER BY sent_at DESC
    LIMIT 1
) last_msg ON TRUE
LEFT JOIN LATERAL (
    SELECT COUNT(*) AS cnt
    FROM messages
    WHERE chat_id = cp.chat_id
      AND sender_user_id <> /* userId */'user1'
      AND read_at IS NULL
) unread ON TRUE
WHERE cp.user_id = /* userId */'user1'
ORDER BY last_msg.sent_at DESC NULLS LAST
