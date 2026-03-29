SELECT /*%expand*/*
FROM messages
WHERE chat_id = /* chatId */'test'
ORDER BY sent_at ASC
