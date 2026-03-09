package com.oversession.controller;

import com.oversession.model.ChatSummary;
import com.oversession.model.Message;
import com.oversession.service.ChatService;

import java.util.List;
import java.util.Optional;

/**
 * Chat REST コントローラー（疑似実装）
 * 実際のSpring Boot環境では @RestController, @RequestMapping などのアノテーションを使用
 */
public class ChatController {
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * GET /api/chats?userId={userId}
     * ユーザーのチャット一覧取得
     */
    public List<ChatSummary> getChats(String userId) {
        return chatService.getUserChats(userId);
    }

    /**
     * GET /api/chats/{chatId}
     * 指定チャット取得
     */
    public Optional<ChatSummary> getChat(String chatId) {
        return chatService.getChatById(chatId);
    }

    /**
     * POST /api/chats
     * チャット作成
     */
    public String createChat(String userId1, String userId2) {
        return chatService.createChat(userId1, userId2);
    }

    /**
     * GET /api/chats/{chatId}/messages
     * チャットメッセージ一覧取得
     */
    public List<Message> getChatMessages(String chatId) {
        return chatService.getChatMessages(chatId);
    }

    /**
     * GET /api/chats/{chatId}/messages?cursor={cursor}&limit={limit}
     * チャットメッセージ一覧取得（ページネーション）
     */
    public List<Message> getChatMessagesWithPagination(String chatId, String cursor, int limit) {
        return chatService.getChatMessagesWithCursor(chatId, cursor, limit);
    }

    /**
     * POST /api/chats/{chatId}/messages
     * メッセージ送信
     */
    public String sendMessage(String chatId, String senderUserId, String text) {
        return chatService.sendMessage(chatId, senderUserId, text);
    }

    /**
     * POST /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション追加
     */
    public void addReaction(String chatId, String messageId, String userId, Integer reactionType) {
        chatService.addReactionToMessage(messageId, userId, reactionType);
    }

    /**
     * DELETE /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション削除
     */
    public void removeReaction(String chatId, String messageId, String userId, Integer reactionType) {
        chatService.removeReactionFromMessage(messageId, userId, reactionType);
    }

    /**
     * PUT /api/chats/{chatId}/messages/{messageId}/read
     * 既読マーク
     */
    public void markAsRead(String chatId, String messageId, String readerId) {
        chatService.markMessageAsRead(messageId, readerId);
    }
}