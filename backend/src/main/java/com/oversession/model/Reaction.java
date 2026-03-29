package com.oversession.model;

import java.util.Objects;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * リアクションエンティティ。
 * reactions テーブルにマッピング (message_id, user_id, reaction_type の複合主キー)
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "reactions")
public class Reaction {

    @Id
    private String messageId;
    @Id
    private String userId;
    @Id
    private Integer reactionType;

    public Reaction() {
    }

    public Reaction(String messageId, String userId, Integer reactionType) {
        this.messageId = messageId;
        this.userId = userId;
        this.reactionType = reactionType;
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

    public Integer getReactionType() {
        return reactionType;
    }

    public void setReactionType(Integer reactionType) {
        this.reactionType = reactionType;
    }

    // --- equals / hashCode / toString ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reaction that = (Reaction) o;
        return Objects.equals(messageId, that.messageId)
                && Objects.equals(userId, that.userId)
                && Objects.equals(reactionType, that.reactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId, reactionType);
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "messageId='" + messageId + '\'' +
                ", userId='" + userId + '\'' +
                ", reactionType=" + reactionType +
                '}';
    }
}
