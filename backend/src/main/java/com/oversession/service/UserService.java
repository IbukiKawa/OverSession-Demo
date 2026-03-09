package com.oversession.service;

import com.oversession.dao.UserDao;
import com.oversession.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User サービスクラス
 */
public class UserService {
    
    private final UserDao userDao;
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> getUserById(String userId) {
        return userDao.findByUserId(userId);
    }

    public String registerUser(User user) {
        return userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(String userId) {
        userDao.deleteByUserId(userId);
    }

    public List<User> searchUsers(String keyword) {
        return userDao.findBySearchCondition(keyword);
    }
}