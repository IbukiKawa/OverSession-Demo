package com.oversession.dao;

import com.oversession.model.ChatSummary;

import java.util.List;
import java.util.Optional;

/**
 * Chat DAO インターフェース
 * frontend Chat一覧管理に対応
 */
public interface ChatDao {

    /**
     * ユーザーのチャット一覧を取得
     */
    List<ChatSummary> findChatsByUserId(String userId);

    /**
     * チャットIDで1件取得
     */
    Optional<ChatSummary> findByChatId(String chatId);

    /**
     * チャットを作成
     */
    String createChat(String userId1, String userId2);

    /**
     * チャット情報を更新（最終メッセージ等）
     */
    void updateChatSummary(ChatSummary chatSummary);

    /**
     * 未読カウントを更新
     */
    void updateUnreadCount(String chatId, String userId, int unreadCount);
}