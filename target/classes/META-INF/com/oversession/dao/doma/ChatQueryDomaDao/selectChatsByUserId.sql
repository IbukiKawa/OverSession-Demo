SELECT
    cp.chat_id,
    partner.user_id        AS partner_user_id,
    partner.user_name      AS partner_user_name,
    partner.picture_name   AS partner_picture_name,
    NULL                   AS last_message,
    NULL::TIMESTAMP        AS last_message_at,
    0                      AS unread_count
FROM chat_participants cp
JOIN chat_participants cp2
    ON cp.chat_id = cp2.chat_id AND cp2.user_id <> /* userId */'user1'
JOIN users partner
    ON partner.user_id = cp2.user_id
WHERE cp.user_id = /* userId */'user1'
ORDER BY cp.chat_id
