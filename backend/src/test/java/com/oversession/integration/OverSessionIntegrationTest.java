package com.oversession.integration;

import com.oversession.dao.impl.InMemoryUserDao;
import com.oversession.dao.impl.InMemoryChatDao;
import com.oversession.dao.impl.InMemoryMessageDao;
import com.oversession.service.UserService;
import com.oversession.service.ChatService;
import com.oversession.controller.UserController;
import com.oversession.controller.ChatController;
import com.oversession.model.User;
import com.oversession.model.ChatSummary;
import com.oversession.model.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * システム全体の統合テスト
 * frontend → controller → service → dao の流れをテスト
 */
class OverSessionIntegrationTest {

    private UserController userController;
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        // DAO層のセットアップ
        InMemoryUserDao userDao = new InMemoryUserDao();
        InMemoryChatDao chatDao = new InMemoryChatDao();
        InMemoryMessageDao messageDao = new InMemoryMessageDao();

        // サービス層のセットアップ
        UserService userService = new UserService(userDao);
        ChatService chatService = new ChatService(chatDao, messageDao);

        // コントローラー層のセットアップ
        userController = new UserController(userService);
        chatController = new ChatController(chatService);
    }

    @Test
    void completeUserWorkflow() {
        // ユーザー登録
        User alice = new User();
        alice.setUserName("Alice");
        alice.setDepartmentName("Engineering");
        alice.setWorkingStatus("出社");
        String aliceId = userController.registerUser(alice);
        assertNotNull(aliceId);

        User bob = new User();
        bob.setUserName("Bob");
        bob.setDepartmentName("Marketing");
        bob.setWorkingStatus("不在");
        String bobId = userController.registerUser(bob);
        assertNotNull(bobId);

        // ユーザー一覧取得
        List<User> users = userController.getUsers();
        assertEquals(2, users.size());

        // ユーザー検索
        List<User> engineeringUsers = userController.searchUsers("engineering");
        assertEquals(1, engineeringUsers.size());
        assertEquals("Alice", engineeringUsers.get(0).getUserName());

        // ユーザー更新
        alice.setUserId(aliceId);
        alice.setWorkingStatus("不在");
        userController.updateUser(aliceId, alice);

        Optional<User> updatedAlice = userController.getUser(aliceId);
        assertTrue(updatedAlice.isPresent());
        assertEquals("不在", updatedAlice.get().getWorkingStatus());
    }

    @Test
    void completeChatWorkflow() {
        // 前準備: ユーザー登録
        User alice = new User();
        alice.setUserName("Alice");
        String aliceId = userController.registerUser(alice);

        User bob = new User();
        bob.setUserName("Bob");
        String bobId = userController.registerUser(bob);

        // チャット作成
        String chatId = chatController.createChat(aliceId, bobId);
        assertNotNull(chatId);

        // チャット一覧取得
        List<ChatSummary> aliceChats = chatController.getChats(aliceId);
        assertEquals(1, aliceChats.size());
        assertEquals(chatId, aliceChats.get(0).getChatId());

        // メッセージ送信
        String messageId1 = chatController.sendMessage(chatId, aliceId, "Hello Bob!");
        assertNotNull(messageId1);

        String messageId2 = chatController.sendMessage(chatId, bobId, "Hi Alice!");
        assertNotNull(messageId2);

        // メッセージ一覧取得
        List<Message> messages = chatController.getChatMessages(chatId);
        assertEquals(2, messages.size());
        assertEquals("Hello Bob!", messages.get(0).getText());
        assertEquals("Hi Alice!", messages.get(1).getText());

        // リアクション追加
        chatController.addReaction(chatId, messageId1, bobId, 1); // Bob が Alice のメッセージに👍

        // 既読マーク
        chatController.markAsRead(chatId, messageId1, bobId);
        chatController.markAsRead(chatId, messageId2, aliceId);

        // メッセージ確認（リアクション・既読状態含む）
        List<Message> updatedMessages = chatController.getChatMessages(chatId);
        assertEquals(2, updatedMessages.size());
        
        Message firstMessage = updatedMessages.get(0);
        assertNotNull(firstMessage.getReadAt());
        assertFalse(firstMessage.getReactions().isEmpty());
    }

    @Test
    void paginationWorkflow() {
        // ユーザー準備
        User alice = new User();
        alice.setUserName("Alice");
        String aliceId = userController.registerUser(alice);

        User bob = new User();
        bob.setUserName("Bob");
        String bobId = userController.registerUser(bob);

        // チャット作成
        String chatId = chatController.createChat(aliceId, bobId);

        // 複数メッセージ送信
        for (int i = 1; i <= 10; i++) {
            chatController.sendMessage(chatId, aliceId, "Message " + i);
        }

        // ページネーション取得
        List<Message> firstPage = chatController.getChatMessagesWithPagination(chatId, null, 5);
        assertEquals(5, firstPage.size());
        assertEquals("Message 1", firstPage.get(0).getText());
        assertEquals("Message 5", firstPage.get(4).getText());

        // 次ページ取得
        String cursor = firstPage.get(4).getMessageId();
        List<Message> secondPage = chatController.getChatMessagesWithPagination(chatId, cursor, 5);
        assertEquals(5, secondPage.size());
        assertEquals("Message 6", secondPage.get(0).getText());
        assertEquals("Message 10", secondPage.get(4).getText());
    }
}