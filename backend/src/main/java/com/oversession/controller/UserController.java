package com.oversession.controller;

import com.oversession.model.User;
import com.oversession.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    // ==================================================================
    // TODO: DB接続後にこのセクションをすべて削除する（ダミーデータ）
    // ==================================================================
    private static final List<User> DUMMY_USERS = new ArrayList<>(Arrays.asList(
        buildDummyUser("user01", "田中 太郎",  "東京本社", null,     "開発部", 1, 3, "男性", 5, "出社", "user02", null),
        buildDummyUser("user02", "鈴木 花子",  "大阪支社", null,     "営業部", 2, 2, "女性", 3, "出社", "user01", null),
        buildDummyUser("user03", "山田 健一",  "東京本社", null,     "人事部", 1, 5, "男性", 8, "不在", null,     null)
    ));
    private static final AtomicInteger DUMMY_USER_ID_COUNTER = new AtomicInteger(4);

    /** ダミーユーザ生成ヘルパー（DB接続後に削除） */
    private static User buildDummyUser(String userId, String userName,
                                       String primary, String secondary,
                                       String dept, Integer officeId, Integer floor,
                                       String gender, Integer affiliationYear,
                                       String status, String matchingUserId, String pictureName) {
        User u = new User();
        u.setUserId(userId);
        u.setUserName(userName);
        u.setPrimaryHeadOfficeName(primary);
        u.setSecondaryHeadOfficeName(secondary);
        u.setDepartmentName(dept);
        u.setOfficeId(officeId);
        u.setFloor(floor);
        u.setGender(gender);
        u.setAffiliationYear(affiliationYear);
        u.setWorkingStatus(status);
        u.setMatchingUserId(matchingUserId);
        u.setPictureName(pictureName);
        u.setDeleted(false);
        return u;
    }
    // ==================================================================
    // TODO: ダミーデータここまで
    // ==================================================================


    /**
     * GET /api/users          → 全ユーザー一覧取得
     * GET /api/users?userId=xxx → 指定ユーザー取得
     */
    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(name = "userId", required = false) String userId) {
        if (userId != null) {
            // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
            // return userService.getUserById(userId)
            //         .map(user -> ResponseEntity.ok((Object) user))
            //         .orElse(ResponseEntity.notFound().build());

            // ↓ ダミーデータから取得（DB接続後に削除）
            return DUMMY_USERS.stream()
                    .filter(u -> u.getUserId().equals(userId) && !u.isDeleted())
                    .findFirst()
                    .map(user -> ResponseEntity.ok((Object) user))
                    .orElse(ResponseEntity.notFound().build());
        }

        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return ResponseEntity.ok(userService.getAllUsers());

        // ↓ ダミーデータから全件取得（DB接続後に削除）
        List<User> active = DUMMY_USERS.stream()
                .filter(u -> !u.isDeleted())
                .collect(Collectors.toList());
        return ResponseEntity.ok(active);
    }


    /**
     * POST /api/users
     * ユーザー登録
     */
    @PostMapping
    public String registerUser(@RequestBody User user) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return userService.registerUser(user);

        // ↓ ダミーデータに追加（DB接続後に削除）
        String newId = String.format("user%02d", DUMMY_USER_ID_COUNTER.getAndIncrement());
        user.setUserId(newId);
        user.setDeleted(false);
        DUMMY_USERS.add(user);
        return newId;
    }


    /**
     * PUT /api/users/{userId}
     * ユーザー更新
     */
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        user.setUserId(userId);

        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // userService.updateUser(user);

        // ↓ ダミーデータを更新（DB接続後に削除）
        for (int i = 0; i < DUMMY_USERS.size(); i++) {
            if (DUMMY_USERS.get(i).getUserId().equals(userId)) {
                DUMMY_USERS.set(i, user);
                return;
            }
        }
    }


    /**
     * DELETE /api/users/{userId}
     * ユーザー削除
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // userService.deleteUser(userId);

        // ↓ ダミーデータをソフトデリート（DB接続後に削除）
        DUMMY_USERS.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .ifPresent(u -> u.setDeleted(true));
    }


    /**
     * GET /api/users/search?keyword={keyword}
     * ユーザー検索
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("keyword") String keyword) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return userService.searchUsers(keyword);

        // ↓ ダミーデータをキーワード検索（DB接続後に削除）
        if (keyword == null || keyword.trim().isEmpty()) {
            return DUMMY_USERS.stream()
                    .filter(u -> !u.isDeleted())
                    .collect(Collectors.toList());
        }
        String kw = keyword.toLowerCase();
        return DUMMY_USERS.stream()
                .filter(u -> !u.isDeleted())
                .filter(u ->
                    u.getUserName().toLowerCase().contains(kw)
                    || (u.getDepartmentName() != null && u.getDepartmentName().toLowerCase().contains(kw))
                    || (u.getPrimaryHeadOfficeName() != null && u.getPrimaryHeadOfficeName().toLowerCase().contains(kw))
                )
                .collect(Collectors.toList());
    }
}
