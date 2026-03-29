SELECT /*%expand*/*
FROM users
WHERE user_id = /* userId */'test'
  AND deleted = false
