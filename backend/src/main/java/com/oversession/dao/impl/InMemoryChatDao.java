package com.oversession.dao.impl;

import com.oversession.dao.ChatDao;
import com.oversession.model.ChatSummary;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ChatDao のインメモリ実装（テスト・開発用）
 */
public class InMemoryChatDao implements ChatDao {

    private final Map<String, ChatSummary> chats = new ConcurrentHashMap<>();
    /** chatId → Set<userId> で参加者を管理 */
    private final Map<String, Set<String>> chatParticipants = new ConcurrentHashMap<>();
    private int idCounter = 1;

    @Override
    public List<ChatSummary> findChatsByUserId(String userId) {
        return chatParticipants.entrySet().stream()
                .filter(entry -> entry.getValue().contains(userId))
                .map(entry -> chats.get(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChatSummary> findByChatId(String chatId) {
        return Optional.ofNullable(chats.get(chatId));
    }

    @Override
    public String createChat(String userId1, String userId2) {
        // 既存チャットがあるかチェック
        for (Map.Entry<String, Set<String>> entry : chatParticipants.entrySet()) {
            Set<String> participants = entry.getValue();
            if (participants.size() == 2 && participants.contains(userId1) && participants.contains(userId2)) {
                return entry.getKey(); // 既存チャットのIDを返す
            }
        }

        // 新規チャット作成
        String chatId = "chat" + (idCounter++);
        
        // TODO: userId2の情報を取得してpartnerUserNameを設定する必要がある
        // 現在は簡易的にuserIdをそのまま使用
        ChatSummary chatSummary = new ChatSummary(chatId, userId2, "User" + userId2);
        
        chats.put(chatId, chatSummary);
        
        Set<String> participants = new HashSet<>();
        participants.add(userId1);
        participants.add(userId2);
        chatParticipants.put(chatId, participants);
        
        return chatId;
    }

    @Override
    public void updateChatSummary(ChatSummary chatSummary) {
        if (chats.containsKey(chatSummary.getChatId())) {
            chats.put(chatSummary.getChatId(), chatSummary);
        }
    }

    @Override
    public void updateUnreadCount(String chatId, String userId, int unreadCount) {
        ChatSummary chat = chats.get(chatId);
        if (chat != null) {
            chat.setUnreadCount(unreadCount);
            chats.put(chatId, chat);
        }
    }

    /**
     * テスト用: チャットの参加者を取得
     */
    public Set<String> getChatParticipants(String chatId) {
        return chatParticipants.getOrDefault(chatId, new HashSet<>());
    }
}