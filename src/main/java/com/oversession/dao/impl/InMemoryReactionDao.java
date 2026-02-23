package com.oversession.dao.impl;

import com.oversession.dao.ReactionDao;
import com.oversession.model.Reaction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * ReactionDao のインメモリ実装（テスト・開発用）。
 */
public class InMemoryReactionDao implements ReactionDao {

    private final List<Reaction> store = new CopyOnWriteArrayList<>();

    @Override
    public List<Reaction> findByMessageId(String messageId) {
        return store.stream()
                .filter(r -> r.getMessageId().equals(messageId))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Reaction reaction) {
        store.add(reaction);
    }

    @Override
    public void delete(String messageId, String userId, String emoji) {
        store.removeIf(r ->
                r.getMessageId().equals(messageId)
                        && r.getUserId().equals(userId)
                        && r.getEmoji().equals(emoji));
    }
}
