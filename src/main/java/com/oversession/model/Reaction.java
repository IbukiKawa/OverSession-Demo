package com.oversession.model;

import java.util.Objects;

/**
 * リアクションエンティティ。
 * API設計書「リアクション送信」に対応。
 */
public class Reaction {

    private String messageId;
    private String userId;
    private String emoji;

    public Reaction() {
    }

    public Reaction(String messageId, String userId, String emoji) {
        this.messageId = messageId;
        this.userId = userId;
        this.emoji = emoji;
    }

    // --- getter / setter ---

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    // --- equals / hashCode / toString ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reaction that = (Reaction) o;
        return Objects.equals(messageId, that.messageId)
                && Objects.equals(userId, that.userId)
                && Objects.equals(emoji, that.emoji);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId, emoji);
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "messageId='" + messageId + '\'' +
                ", userId='" + userId + '\'' +
                ", emoji='" + emoji + '\'' +
                '}';
    }
}
