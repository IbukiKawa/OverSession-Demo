SELECT /*%expand*/*
FROM users
WHERE deleted = false
  AND (
    LOWER(user_name)                LIKE /* @infix(keyword) */'%test%'
    OR LOWER(department_name)       LIKE /* @infix(keyword) */'%test%'
    OR LOWER(primary_head_office_name) LIKE /* @infix(keyword) */'%test%'
  )
ORDER BY user_id
