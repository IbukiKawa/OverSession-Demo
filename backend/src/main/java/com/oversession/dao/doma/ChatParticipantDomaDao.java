package com.oversession.dao.doma;

import com.oversession.model.ChatParticipant;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * ChatParticipant Doma DAO インターフェース
 * SQL ファイル: META-INF/com/oversession/dao/doma/ChatParticipantDomaDao/
 */
@Dao
@ConfigAutowireable
public interface ChatParticipantDomaDao {

    /** 2人のユーザーが共通で参加しているチャットIDを取得（既存チャット確認用）*/
    @Select
    List<String> findCommonChatIds(String userId1, String userId2);

    @Insert
    int insert(ChatParticipant chatParticipant);
}
