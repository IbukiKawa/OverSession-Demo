package com.oversession.dao;

import com.oversession.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * メッセージ DAO インターフェース。
 * <ul>
 *   <li>GET  /api/rooms/{roomId}/messages — メッセージ履歴取得</li>
 *   <li>POST /api/rooms/{roomId}/messages — メッセージ送信</li>
 * </ul>
 */
public interface MessageDao {

    /**
     * 指定ルームのメッセージ一覧を取得する（時系列昇順）。
     */
    List<Message> findByRoomId(String roomId);

    /**
     * メッセージIDで1件取得する。
     */
    Optional<Message> findById(String messageId);

    /**
     * メッセージを保存（送信）する。
     */
    void save(Message message);

    /**
     * メッセージを削除する。
     */
    void deleteById(String messageId);
}
