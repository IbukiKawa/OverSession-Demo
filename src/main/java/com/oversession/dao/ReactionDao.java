package com.oversession.dao;

import com.oversession.model.Reaction;

import java.util.List;

/**
 * リアクション DAO インターフェース。
 * <ul>
 *   <li>POST /api/rooms/{roomId}/messages/{messageId}/reactions — リアクション送信</li>
 * </ul>
 */
public interface ReactionDao {

    /**
     * 指定メッセージのリアクション一覧を取得する。
     */
    List<Reaction> findByMessageId(String messageId);

    /**
     * リアクションを保存（追加）する。
     */
    void save(Reaction reaction);

    /**
     * リアクションを削除する（同一ユーザー・同一絵文字の取り消し）。
     */
    void delete(String messageId, String userId, String emoji);
}
