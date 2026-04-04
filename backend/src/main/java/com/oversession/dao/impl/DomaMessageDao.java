package com.oversession.dao.impl;

import com.oversession.dao.MessageDao;
import com.oversession.dao.doma.MessageDomaDao;
import com.oversession.dao.doma.ReactionDomaDao;
import com.oversession.model.Message;
import com.oversession.model.MessageReaction;
import com.oversession.model.Reaction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MessageDao の Doma 実装（PostgreSQL 永続化）
 * SQL は META-INF/com/oversession/dao/doma/MessageDomaDao/ 配下の .sql ファイルで管理
 */
@Repository
@ConditionalOnProperty(name = "message.dao.type", havingValue = "doma")
public class DomaMessageDao implements MessageDao {

    private final MessageDomaDao messageDomaDao;
    private final ReactionDomaDao reactionDomaDao;

    public DomaMessageDao(MessageDomaDao messageDomaDao, ReactionDomaDao reactionDomaDao) {
        this.messageDomaDao = messageDomaDao;
        this.reactionDomaDao = reactionDomaDao;
    }

    @Override
    public List<Message> findMessagesByChatId(String chatId) {
        List<Message> messages = messageDomaDao.selectByChatId(chatId);
        attachReactions(messages);
        return messages;
    }

    @Override
    public List<Message> findMessagesByChatIdWithCursor(String chatId, String cursor, int limit) {
        List<Message> messages = messageDomaDao.selectByChatIdWithCursor(chatId, cursor, limit);
        // selectByChatIdWithCursor.sql は DESC で返るので時系列順に戻す
        Collections.reverse(messages);
        attachReactions(messages);
        return messages;
    }

    @Override
    public Optional<Message> findByMessageId(String messageId) {
        Optional<Message> message = messageDomaDao.selectByMessageId(messageId);
        message.ifPresent(m -> attachReactions(List.of(m)));
        return message;
    }

    @Override
    public String sendMessage(Message message) {
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            message.setMessageId("msg" + UUID.randomUUID().toString().substring(0, 8));
        }
        if (message.getSentAt() == null) {
            message.setSentAt(LocalDateTime.now());
        }
        messageDomaDao.insert(message);
        return message.getMessageId();
    }

    @Override
    public void markAsRead(String messageId, String readerId) {
        messageDomaDao.selectByMessageId(messageId).ifPresent(message -> {
            message.setReadAt(LocalDateTime.now());
            messageDomaDao.update(message);
        });
    }

    @Override
    public void addReaction(String messageId, String userId, Integer reactionType) {
        Reaction reaction = new Reaction(messageId, userId, reactionType);
        reactionDomaDao.insertIgnoreConflict(reaction);
    }

    @Override
    public void removeReaction(String messageId, String userId, Integer reactionType) {
        Reaction reaction = new Reaction(messageId, userId, reactionType);
        reactionDomaDao.delete(reaction);
    }

    /**
     * メッセージリストにリアクション情報をバッチ付与する。
     * メッセージIDのセットを使い、DAO を個別に呼ぶ（N+1 は許容範囲）。
     */
    private void attachReactions(List<Message> messages) {
        if (messages.isEmpty()) return;

        // messageId → List<Reaction> のマップを構築
        Map<String, List<Reaction>> reactionMap = new HashMap<>();
        for (Message m : messages) {
            List<Reaction> reactions = reactionDomaDao.selectByMessageId(m.getMessageId());
            reactionMap.put(m.getMessageId(), reactions);
        }

        for (Message m : messages) {
            List<Reaction> reactions = reactionMap.getOrDefault(m.getMessageId(), Collections.emptyList());

            // reaction_type ごとにカウントを集計
            Map<Integer, Integer> countMap = new HashMap<>();
            for (Reaction r : reactions) {
                countMap.merge(r.getReactionType(), 1, Integer::sum);
            }

            List<MessageReaction> messageReactions = countMap.entrySet().stream()
                    .map(e -> new MessageReaction(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            m.setReactions(messageReactions);
        }
    }
}
