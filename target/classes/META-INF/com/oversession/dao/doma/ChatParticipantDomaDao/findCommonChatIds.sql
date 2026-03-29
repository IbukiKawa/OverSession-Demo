SELECT cp1.chat_id
FROM chat_participants cp1
JOIN chat_participants cp2
    ON cp1.chat_id = cp2.chat_id
WHERE cp1.user_id = /* userId1 */'user1'
  AND cp2.user_id = /* userId2 */'user2'
