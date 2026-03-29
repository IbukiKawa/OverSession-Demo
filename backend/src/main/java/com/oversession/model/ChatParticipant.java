package com.oversession.model;

import java.util.Objects;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * チャット参加者エンティティ - chat_participants テーブルに対応
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "chat_participants")
public class ChatParticipant {

    @Id
    private String chatId;
    @Id
    private String userId;

    public ChatParticipant() {
    }

    public ChatParticipant(String chatId, String userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatParticipant that = (ChatParticipant) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userId);
    }
}
