SELECT /*%expand*/*
FROM messages
WHERE chat_id = /* chatId */'test'
/*%if cursor != null && !cursor.isEmpty() */
  AND sent_at < (
    SELECT sent_at FROM messages WHERE message_id = /* cursor */'msg1'
  )
/*%end*/
ORDER BY sent_at DESC
LIMIT /* limit */20
