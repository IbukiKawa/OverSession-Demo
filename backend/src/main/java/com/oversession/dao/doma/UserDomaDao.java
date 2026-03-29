package com.oversession.dao.doma;

import com.oversession.model.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * User Doma DAO インターフェース
 * SQL ファイル: META-INF/com/oversession/dao/doma/UserDomaDao/
 */
@Dao
@ConfigAutowireable
public interface UserDomaDao {

    @Select
    List<User> selectAll();

    @Select
    Optional<User> selectByUserId(String userId);

    @Select
    List<User> searchByKeyword(String keyword);

    @Insert
    int insert(User user);

    @Update
    int update(User user);
}
