SELECT /*%expand*/*
FROM users
WHERE deleted = false
ORDER BY user_id
