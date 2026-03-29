package com.oversession.dao.doma;

import com.oversession.model.Message;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * Message Doma DAO インターフェース
 * SQL ファイル: META-INF/com/oversession/dao/doma/MessageDomaDao/
 */
@Dao
@ConfigAutowireable
public interface MessageDomaDao {

    @Select
    List<Message> selectByChatId(String chatId);

    @Select
    List<Message> selectByChatIdWithCursor(String chatId, String cursor, int limit);

    @Select
    Optional<Message> selectByMessageId(String messageId);

    @Insert
    int insert(Message message);

    @Update
    int update(Message message);
}
