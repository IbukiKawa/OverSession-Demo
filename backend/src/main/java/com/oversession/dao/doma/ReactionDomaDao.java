package com.oversession.dao.doma;

import com.oversession.model.Reaction;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * Reaction Doma DAO インターフェース
 * SQL ファイル: META-INF/com/oversession/dao/doma/ReactionDomaDao/
 */
@Dao
@ConfigAutowireable
public interface ReactionDomaDao {

    @Select
    List<Reaction> selectByMessageId(String messageId);

    /** ON CONFLICT DO NOTHING 相当 - sqlFile=true でカスタムSQLを使用 */
    @Insert(sqlFile = true)
    int insertIgnoreConflict(Reaction reaction);

    @Delete
    int delete(Reaction reaction);
}
