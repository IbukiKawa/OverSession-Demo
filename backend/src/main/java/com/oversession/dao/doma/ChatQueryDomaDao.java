package com.oversession.dao.doma;

import com.oversession.model.ChatSummary;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * チャット一覧・詳細取得用 Doma DAO インターフェース（SELECT専用）
 * chat_participants + users + messages を JOIN して ChatSummary を組み立てる
 * SQL ファイル: META-INF/com/oversession/dao/doma/ChatQueryDomaDao/
 */
@Dao
@ConfigAutowireable
public interface ChatQueryDomaDao {

    /** ユーザーが参加している全チャットを取得（LATERAL JOIN で最新メッセージ・未読数も取得）*/
    @Select
    List<ChatSummary> selectChatsByUserId(String userId);

    /** chatId からチャット情報を1件取得 */
    @Select
    Optional<ChatSummary> selectByChatId(String chatId);
}
