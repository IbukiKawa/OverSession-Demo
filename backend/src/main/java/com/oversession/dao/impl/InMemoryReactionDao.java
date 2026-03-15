package com.oversession.dao.impl;

import com.oversession.model.Reaction;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ReactionDao のインメモリ実装（テスト・開発用）
 */
public class InMemoryReactionDao {

    private final Map<String, Reaction> reactions = new ConcurrentHashMap<>();
    private int idCounter = 1;

    public void save(Reaction reaction) {
        String id = reaction.getMessageId() + "_" + reaction.getUserId() + "_" + reaction.getEmoji();
        reactions.put(id, reaction);
    }

    public Optional<Reaction> findById(String reactionId) {
        return Optional.ofNullable(reactions.get(reactionId));
    }

    public List<Reaction> findByMessageId(String messageId) {
        return reactions.values().stream()
                .filter(reaction -> reaction.getMessageId().equals(messageId))
                .collect(Collectors.toList());
    }

    public void delete(String messageId, String userId, String emoji) {
        String id = messageId + "_" + userId + "_" + emoji;
        reactions.remove(id);
    }

    public void deleteById(String reactionId) {
        reactions.remove(reactionId);
    }

    public void clear() {
        reactions.clear();
    }
}