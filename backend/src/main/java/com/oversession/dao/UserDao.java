package com.oversession.dao;

import com.oversession.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User DAO インターフェース
 * frontend User管理に対応
 */
public interface UserDao {

    /**
     * 全ユーザー一覧を取得（削除されていないもののみ）
     */
    List<User> findAll();

    /**
     * userIdで1件取得
     */
    Optional<User> findByUserId(String userId);

    /**
     * ユーザーを登録
     */
    String save(User user);

    /**
     * ユーザー情報を更新
     */
    void update(User user);

    /**
     * ユーザーを論理削除
     */
    void deleteByUserId(String userId);

    /**
     * 検索条件でユーザー一覧を取得
     */
    List<User> findBySearchCondition(String keyword);
}