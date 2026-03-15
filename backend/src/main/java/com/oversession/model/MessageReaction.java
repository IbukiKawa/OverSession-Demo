package com.oversession.model;

import java.util.Objects;

/**
 * メッセージリアクションエンティティ - frontend types.tsのReaction interfaceに対応
 * type: 1 | 2 | 3 | 4
 */
public class MessageReaction {
    private Integer type; // ReactionType: 1 | 2 | 3 | 4
    private Integer count;
    private boolean reactedByMe;

    public MessageReaction() {
        this.count = 0;
        this.reactedByMe = false;
    }

    public MessageReaction(Integer type, Integer count) {
        this.type = type;
        this.count = count;
        this.reactedByMe = false;
    }

    public MessageReaction(Integer type, Integer count, boolean reactedByMe) {
        this.type = type;
        this.count = count;
        this.reactedByMe = reactedByMe;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isReactedByMe() {
        return reactedByMe;
    }

    public void setReactedByMe(boolean reactedByMe) {
        this.reactedByMe = reactedByMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageReaction that = (MessageReaction) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "MessageReaction{" +
                "type=" + type +
                ", count=" + count +
                ", reactedByMe=" + reactedByMe +
                '}';
    }
}