package com.oversession.service;

import com.oversession.dao.ChatDao;
import com.oversession.dao.MessageDao;
import com.oversession.model.ChatSummary;
import com.oversession.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * Chat サービスクラス
 */
public class ChatService {
    
    private final ChatDao chatDao;
    private final MessageDao messageDao;
    
    public ChatService(ChatDao chatDao, MessageDao messageDao) {
        this.chatDao = chatDao;
        this.messageDao = messageDao;
    }

    public List<ChatSummary> getUserChats(String userId) {
        return chatDao.findChatsByUserId(userId);
    }

    public Optional<ChatSummary> getChatById(String chatId) {
        return chatDao.findByChatId(chatId);
    }

    public String createChat(String userId1, String userId2) {
        return chatDao.createChat(userId1, userId2);
    }

    public List<Message> getChatMessages(String chatId) {
        return messageDao.findMessagesByChatId(chatId);
    }

    public List<Message> getChatMessagesWithCursor(String chatId, String cursor, int limit) {
        return messageDao.findMessagesByChatIdWithCursor(chatId, cursor, limit);
    }

    public String sendMessage(String chatId, String senderUserId, String text) {
        Message message = new Message();
        message.setChatId(chatId);
        message.setSenderUserId(senderUserId);
        message.setText(text);
        
        return messageDao.sendMessage(message);
    }

    public void addReactionToMessage(String messageId, String userId, Integer reactionType) {
        messageDao.addReaction(messageId, userId, reactionType);
    }

    public void removeReactionFromMessage(String messageId, String userId, Integer reactionType) {
        messageDao.removeReaction(messageId, userId, reactionType);
    }

    public void markMessageAsRead(String messageId, String readerId) {
        messageDao.markAsRead(messageId, readerId);
    }
}