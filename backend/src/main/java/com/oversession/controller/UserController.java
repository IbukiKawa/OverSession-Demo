package com.oversession.controller;

import com.oversession.model.User;
import com.oversession.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * User REST コントローラー（疑似実装）
 * 実際のSpring Boot環境では @RestController, @RequestMapping などのアノテーションを使用
 */
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users
     * 全ユーザー一覧取得
     */
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    /**
     * GET /api/users/{userId}
     * 指定ユーザー取得
     */
    public Optional<User> getUser(String userId) {
        return userService.getUserById(userId);
    }

    /**
     * POST /api/users
     * ユーザー登録
     */
    public String registerUser(User user) {
        return userService.registerUser(user);
    }

    /**
     * PUT /api/users/{userId}
     * ユーザー更新
     */
    public void updateUser(String userId, User user) {
        user.setUserId(userId);
        userService.updateUser(user);
    }

    /**
     * DELETE /api/users/{userId}
     * ユーザー削除
     */
    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    /**
     * GET /api/users/search?keyword={keyword}
     * ユーザー検索
     */
    public List<User> searchUsers(String keyword) {
        return userService.searchUsers(keyword);
    }
}