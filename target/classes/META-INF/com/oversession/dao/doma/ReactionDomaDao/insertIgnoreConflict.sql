INSERT INTO reactions (message_id, user_id, reaction_type)
VALUES (/* reaction.messageId */'msg1', /* reaction.userId */'user1', /* reaction.reactionType */1)
ON CONFLICT DO NOTHING
