package com.oversession.controller;

import com.oversession.model.User;
import com.oversession.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * User REST コントローラー
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users          → 全ユーザー一覧取得
     * GET /api/users?userId=xxx → 指定ユーザー取得
     */
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String userId) {
        if (userId != null) {
            return userService.getUserById(userId)
                    .map(user -> ResponseEntity.ok((Object) user))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * POST /api/users
     * ユーザー登録
     */
    @PostMapping
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    /**
     * PUT /api/users/{userId}
     * ユーザー更新
     */
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable String userId, @RequestBody User user) {
        user.setUserId(userId);
        userService.updateUser(user);
    }

    /**
     * DELETE /api/users/{userId}
     * ユーザー削除
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    /**
     * GET /api/users/search?keyword={keyword}
     * ユーザー検索
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }
}