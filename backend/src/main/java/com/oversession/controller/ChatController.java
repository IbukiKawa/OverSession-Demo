package com.oversession.controller;

import com.oversession.model.ChatSummary;
import com.oversession.model.Message;
import com.oversession.service.ChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Chat REST コントローラー
 */
@RestController
@RequestMapping("/api/chats")
public class ChatController {
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * GET /api/chats?userId={userId}
     * ユーザーのチャット一覧取得
     */
    @GetMapping
    public List<ChatSummary> getChats(@RequestParam String userId) {
        return chatService.getUserChats(userId);
    }

    /**
     * GET /api/chats/{chatId}
     * 指定チャット取得
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatSummary> getChat(@PathVariable String chatId) {
        return chatService.getChatById(chatId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/chats
     * チャット作成
     */
    @PostMapping
    public String createChat(@RequestBody Map<String, String> body) {
        return chatService.createChat(body.get("userId1"), body.get("userId2"));
    }

    /**
     * GET /api/chats/{chatId}/messages
     * チャットメッセージ一覧取得
     */
    @GetMapping("/{chatId}/messages")
    public List<Message> getChatMessages(
            @PathVariable String chatId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int limit) {
        return chatService.getChatMessagesWithCursor(chatId, cursor, limit);
    }

    /**
     * POST /api/chats/{chatId}/messages
     * メッセージ送信
     */
    @PostMapping("/{chatId}/messages")
    public String sendMessage(@PathVariable String chatId, @RequestBody Map<String, String> body) {
        return chatService.sendMessage(chatId, body.get("senderUserId"), body.get("text"));
    }

    /**
     * POST /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション追加
     */
    @PostMapping("/{chatId}/messages/{messageId}/reactions")
    public void addReaction(
            @PathVariable String chatId,
            @PathVariable String messageId,
            @RequestBody Map<String, Object> body) {
        chatService.addReactionToMessage(
                messageId,
                (String) body.get("userId"),
                (Integer) body.get("reactionType"));
    }

    /**
     * DELETE /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション削除
     */
    @DeleteMapping("/{chatId}/messages/{messageId}/reactions")
    public void removeReaction(
            @PathVariable String chatId,
            @PathVariable String messageId,
            @RequestBody Map<String, Object> body) {
        chatService.removeReactionFromMessage(
                messageId,
                (String) body.get("userId"),
                (Integer) body.get("reactionType"));
    }

    /**
     * PUT /api/chats/{chatId}/messages/{messageId}/read
     * 既読マーク
     */
    @PutMapping("/{chatId}/messages/{messageId}/read")
    public void markAsRead(
            @PathVariable String chatId,
            @PathVariable String messageId,
            @RequestBody Map<String, String> body) {
        chatService.markMessageAsRead(messageId, body.get("readerId"));
    }
}