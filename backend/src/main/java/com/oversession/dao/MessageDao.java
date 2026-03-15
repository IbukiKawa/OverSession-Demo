package com.oversession.dao;

import com.oversession.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * Message DAO インターフェース
 * frontend メッセージ履歴取得・送信に対応
 */
public interface MessageDao {

    /**
     * 指定チャットのメッセージ一覧を取得（時系列順）
     */
    List<Message> findMessagesByChatId(String chatId);

    /**
     * カーソルベースのページネーション対応メッセージ取得
     */
    List<Message> findMessagesByChatIdWithCursor(String chatId, String cursor, int limit);

    /**
     * メッセージIDで1件取得
     */
    Optional<Message> findByMessageId(String messageId);

    /**
     * メッセージを送信（保存）
     */
    String sendMessage(Message message);

    /**
     * メッセージの既読状態を更新
     */
    void markAsRead(String messageId, String readerId);

    /**
     * メッセージにリアクションを追加
     */
    void addReaction(String messageId, String userId, Integer reactionType);

    /**
     * メッセージのリアクションを削除
     */
    void removeReaction(String messageId, String userId, Integer reactionType);
}
