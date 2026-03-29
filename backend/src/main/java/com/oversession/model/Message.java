package com.oversession.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Transient;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * メッセージエンティティ - frontend types.tsのMessage interfaceに対応
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "messages")
public class Message {
    @Id
    private String messageId;
    private String chatId;
    private String senderUserId;
    private String text;
    private LocalDateTime sentAt;
    @Transient
    private List<MessageReaction> reactions;
    private LocalDateTime readAt; // null=未読

    public Message() {
        this.reactions = new ArrayList<>();
    }

    public Message(String messageId, String chatId, String senderUserId, String text, LocalDateTime sentAt) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderUserId = senderUserId;
        this.text = text;
        this.sentAt = sentAt;
        this.reactions = new ArrayList<>();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public List<MessageReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<MessageReaction> reactions) {
        this.reactions = reactions;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", chatId='" + chatId + '\'' +
                ", senderUserId='" + senderUserId + '\'' +
                ", text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", readAt=" + readAt +
                '}';
    }
}
