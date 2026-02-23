package com.oversession.dao.impl;

import com.oversession.dao.MessageDao;
import com.oversession.model.Message;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * MessageDao のインメモリ実装（テスト・開発用）。
 */
public class InMemoryMessageDao implements MessageDao {

    private final Map<String, Message> store = new ConcurrentHashMap<>();

    @Override
    public List<Message> findByRoomId(String roomId) {
        return store.values().stream()
                .filter(m -> m.getRoomId().equals(roomId))
                .sorted(Comparator.comparing(Message::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findById(String messageId) {
        return Optional.ofNullable(store.get(messageId));
    }

    @Override
    public void save(Message message) {
        store.put(message.getMessageId(), message);
    }

    @Override
    public void deleteById(String messageId) {
        store.remove(messageId);
    }
}
