package com.oversession.dao.impl;

import com.oversession.dao.ChatDao;
import com.oversession.dao.doma.ChatParticipantDomaDao;
import com.oversession.dao.doma.ChatQueryDomaDao;
import com.oversession.model.ChatParticipant;
import com.oversession.model.ChatSummary;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ChatDao の Doma 実装（PostgreSQL 永続化）
 * チャット一覧取得 SQL は META-INF/com/oversession/dao/doma/ChatQueryDomaDao/ で管理
 * チャット作成は chat_participants への INSERT のみ（chat_summary テーブルは存在しない）
 */
@Repository
@Primary
public class DomaChatDao implements ChatDao {

    private final ChatQueryDomaDao chatQueryDomaDao;
    private final ChatParticipantDomaDao chatParticipantDomaDao;

    public DomaChatDao(ChatQueryDomaDao chatQueryDomaDao,
                       ChatParticipantDomaDao chatParticipantDomaDao) {
        this.chatQueryDomaDao = chatQueryDomaDao;
        this.chatParticipantDomaDao = chatParticipantDomaDao;
    }

    @Override
    public List<ChatSummary> findChatsByUserId(String userId) {
        return chatQueryDomaDao.selectChatsByUserId(userId);
    }

    @Override
    public Optional<ChatSummary> findByChatId(String chatId) {
        return chatQueryDomaDao.selectByChatId(chatId);
    }

    @Override
    public String createChat(String userId1, String userId2) {
        // 既存チャットがあればそのIDを返す
        List<String> existing = chatParticipantDomaDao.findCommonChatIds(userId1, userId2);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        // 新規チャット作成: chat_participants に2件 INSERT するだけ
        String chatId = "chat" + UUID.randomUUID().toString().substring(0, 8);
        chatParticipantDomaDao.insert(new ChatParticipant(chatId, userId1));
        chatParticipantDomaDao.insert(new ChatParticipant(chatId, userId2));
        return chatId;
    }

    @Override
    public void updateChatSummary(ChatSummary chatSummary) {
        // ChatSummary は chat_participants + messages から動的に生成されるため更新不要
    }

    @Override
    public void updateUnreadCount(String chatId, String userId, int unreadCount) {
        // 未読数は messages.read_at から算出するため直接更新しない
    }
}
