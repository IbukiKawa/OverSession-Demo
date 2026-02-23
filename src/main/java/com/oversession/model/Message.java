package com.oversession.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * チャットメッセージエンティティ。
 * API設計書「メッセージ履歴取得」「メッセージ送信」に対応。
 */
public class Message {

    private String messageId;
    private String roomId;
    private String userId;
    private String message;
    private LocalDateTime timestamp;
    private List<Reaction> reactions;

    public Message() {
        this.reactions = new ArrayList<>();
    }

    public Message(String messageId, String roomId, String userId,
                   String message, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.reactions = new ArrayList<>();
    }

    // --- getter / setter ---

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    // --- equals / hashCode / toString ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message that = (Message) o;
        return Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", reactions=" + reactions +
                '}';
    }
}
