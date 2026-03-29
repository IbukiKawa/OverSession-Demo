package com.oversession.model;

import java.time.LocalDateTime;
import java.util.Objects;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * チャットサマリー - SELECT結果マッピング用（DBテーブルなし）
 * chat_participants + messages + users を結合して生成する
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class ChatSummary {
    @Id
    private String chatId;
    private String partnerUserId;
    private String partnerUserName;
    private String partnerPictureName;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private Integer unreadCount;

    public ChatSummary() {
        this.unreadCount = 0;
    }

    public ChatSummary(String chatId, String partnerUserId, String partnerUserName) {
        this.chatId = chatId;
        this.partnerUserId = partnerUserId;
        this.partnerUserName = partnerUserName;
        this.unreadCount = 0;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getPartnerUserName() {
        return partnerUserName;
    }

    public void setPartnerUserName(String partnerUserName) {
        this.partnerUserName = partnerUserName;
    }

    public String getPartnerPictureName() {
        return partnerPictureName;
    }

    public void setPartnerPictureName(String partnerPictureName) {
        this.partnerPictureName = partnerPictureName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatSummary that = (ChatSummary) o;
        return Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public String toString() {
        return "ChatSummary{" +
                "chatId='" + chatId + '\'' +
                ", partnerUserId='" + partnerUserId + '\'' +
                ", partnerUserName='" + partnerUserName + '\'' +
                ", unreadCount=" + unreadCount +
                '}';
    }
}