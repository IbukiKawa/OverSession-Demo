package com.oversession.dao.impl;

import com.oversession.dao.UserDao;
import com.oversession.dao.doma.UserDomaDao;
import com.oversession.model.User;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * UserDao の Doma 実装（PostgreSQL 永続化）
 * SQL は META-INF/com/oversession/dao/doma/UserDomaDao/ 配下の .sql ファイルで管理
 */
@Repository
@Primary
public class DomaUserDao implements UserDao {

    private final UserDomaDao userDomaDao;

    public DomaUserDao(UserDomaDao userDomaDao) {
        this.userDomaDao = userDomaDao;
    }

    @Override
    public List<User> findAll() {
        return userDomaDao.selectAll();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userDomaDao.selectByUserId(userId);
    }

    @Override
    public String save(User user) {
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId("user" + UUID.randomUUID().toString().substring(0, 8));
        }
        userDomaDao.insert(user);
        return user.getUserId();
    }

    @Override
    public void update(User user) {
        userDomaDao.update(user);
    }

    @Override
    public void deleteByUserId(String userId) {
        userDomaDao.selectByUserId(userId).ifPresent(user -> {
            user.setDeleted(true);
            userDomaDao.update(user);
        });
    }

    @Override
    public List<User> findBySearchCondition(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        return userDomaDao.searchByKeyword(keyword.toLowerCase());
    }
}
