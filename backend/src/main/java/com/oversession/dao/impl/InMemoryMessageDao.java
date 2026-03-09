package com.oversession.dao.impl;

import com.oversession.dao.MessageDao;
import com.oversession.model.Message;
import com.oversession.model.MessageReaction;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * MessageDao のインメモリ実装（テスト・開発用）
 */
public class InMemoryMessageDao implements MessageDao {

    private final Map<String, Message> messages = new ConcurrentHashMap<>();
    /** messageId → Map<userId, Set<reactionType>> でリアクション管理 */
    private final Map<String, Map<String, Set<Integer>>> messageReactions = new ConcurrentHashMap<>();
    private int idCounter = 1;

    @Override
    public List<Message> findMessagesByChatId(String chatId) {
        return messages.values().stream()
                .filter(msg -> msg.getChatId().equals(chatId))
                .sorted(Comparator.comparing(Message::getSentAt))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> findMessagesByChatIdWithCursor(String chatId, String cursor, int limit) {
        List<Message> allMessages = findMessagesByChatId(chatId);
        
        int startIndex = 0;
        if (cursor != null && !cursor.isEmpty()) {
            // カーソルはメッセージIDとして扱う
            for (int i = 0; i < allMessages.size(); i++) {
                if (allMessages.get(i).getMessageId().equals(cursor)) {
                    startIndex = i + 1;
                    break;
                }
            }
        }
        
        return allMessages.stream()
                .skip(startIndex)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findByMessageId(String messageId) {
        return Optional.ofNullable(messages.get(messageId));
    }

    @Override
    public String sendMessage(Message message) {
        if (message.getMessageId() == null) {
            message.setMessageId("msg" + (idCounter++));
        }
        if (message.getSentAt() == null) {
            message.setSentAt(LocalDateTime.now());
        }
        
        // リアクション情報を更新
        updateMessageReactions(message);
        
        messages.put(message.getMessageId(), message);
        return message.getMessageId();
    }

    @Override
    public void markAsRead(String messageId, String readerId) {
        Message message = messages.get(messageId);
        if (message != null) {
            message.setReadAt(LocalDateTime.now());
            messages.put(messageId, message);
        }
    }

    @Override
    public void addReaction(String messageId, String userId, Integer reactionType) {
        messageReactions
                .computeIfAbsent(messageId, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(userId, k -> new HashSet<>())
                .add(reactionType);
        
        // メッセージのリアクション情報を更新
        updateMessageReactionsForMessage(messageId);
    }

    @Override
    public void removeReaction(String messageId, String userId, Integer reactionType) {
        Map<String, Set<Integer>> reactions = messageReactions.get(messageId);
        if (reactions != null) {
            Set<Integer> userReactions = reactions.get(userId);
            if (userReactions != null) {
                userReactions.remove(reactionType);
                if (userReactions.isEmpty()) {
                    reactions.remove(userId);
                }
            }
        }
        
        // メッセージのリアクション情報を更新
        updateMessageReactionsForMessage(messageId);
    }

    private void updateMessageReactions(Message message) {
        Map<String, Set<Integer>> reactions = messageReactions.get(message.getMessageId());
        if (reactions != null) {
            // リアクションタイプごとにカウント
            Map<Integer, Integer> reactionCounts = new HashMap<>();
            for (Set<Integer> userReactions : reactions.values()) {
                for (Integer reactionType : userReactions) {
                    reactionCounts.merge(reactionType, 1, Integer::sum);
                }
            }
            
            // MessageReactionリストを作成
            List<MessageReaction> messageReactionList = reactionCounts.entrySet().stream()
                    .map(entry -> new MessageReaction(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            
            message.setReactions(messageReactionList);
        }
    }

    private void updateMessageReactionsForMessage(String messageId) {
        Message message = messages.get(messageId);
        if (message != null) {
            updateMessageReactions(message);
            messages.put(messageId, message);
        }
    }
    
    // テスト用メソッド
    public void save(Message message) {
        sendMessage(message);
    }
    
    public Optional<Message> findById(String messageId) {
        return findByMessageId(messageId);
    }
    
    public List<Message> findByRoomId(String roomId) {
        return findMessagesByChatId(roomId);
    }
    
    public void deleteById(String messageId) {
        messages.remove(messageId);
        messageReactions.remove(messageId);
    }
}