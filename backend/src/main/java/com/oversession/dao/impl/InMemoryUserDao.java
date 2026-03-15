package com.oversession.dao.impl;

import com.oversession.dao.UserDao;
import com.oversession.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * UserDao のインメモリ実装（テスト・開発用）
 */
public class InMemoryUserDao implements UserDao {

    private final Map<String, User> users = new ConcurrentHashMap<>();
    private int idCounter = 1;

    @Override
    public List<User> findAll() {
        return users.values().stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        User user = users.get(userId);
        return user != null && !user.isDeleted() ? Optional.of(user) : Optional.empty();
    }

    @Override
    public String save(User user) {
        if (user.getUserId() == null) {
            user.setUserId("user" + (idCounter++));
        }
        users.put(user.getUserId(), user);
        return user.getUserId();
    }

    @Override
    public void update(User user) {
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
        }
    }

    @Override
    public void deleteByUserId(String userId) {
        User user = users.get(userId);
        if (user != null) {
            user.setDeleted(true);
            users.put(userId, user);
        }
    }

    @Override
    public List<User> findBySearchCondition(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        return users.values().stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> 
                    user.getUserName().toLowerCase().contains(lowerKeyword) ||
                    (user.getDepartmentName() != null && user.getDepartmentName().toLowerCase().contains(lowerKeyword)) ||
                    (user.getPrimaryHeadOfficeName() != null && user.getPrimaryHeadOfficeName().toLowerCase().contains(lowerKeyword))
                )
                .collect(Collectors.toList());
    }
}