package com.oversession.dao.impl;

import com.oversession.dao.MessageDao;
import com.oversession.dao.dynamodb.DynamoDbClientWrapper;
import com.oversession.model.Message;
import com.oversession.model.MessageReaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
public class DynamoMessageDao implements MessageDao {

    private static final String TABLE_NAME = "Messages";
    private static final String GSI_MESSAGE_ID = "MessageIdIndex";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final DynamoDbClientWrapper wrapper;

    public DynamoMessageDao(DynamoDbClientWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public List<Message> findMessagesByChatId(String chatId) {
        List<Map<String, AttributeValue>> items = wrapper.query(TABLE_NAME,
                "RoomId = :rid",
                null,
                Map.of(":rid", AttributeValue.builder().s(chatId).build()),
                true, null);

        return items.stream().map(this::toMessage).collect(Collectors.toList());
    }

    @Override
    public List<Message> findMessagesByChatIdWithCursor(String chatId, String cursor, int limit) {
        if (cursor != null && !cursor.isEmpty()) {
            // Find cursor message's SortKey via GSI
            List<Map<String, AttributeValue>> cursorItems = wrapper.queryWithIndex(
                    TABLE_NAME, GSI_MESSAGE_ID,
                    "MessageId = :mid",
                    null,
                    Map.of(":mid", AttributeValue.builder().s(cursor).build()));

            if (cursorItems.isEmpty()) {
                return Collections.emptyList();
            }

            String cursorSortKey = cursorItems.get(0).get("SortKey").s();

            // Query messages before cursor (DESC), then reverse
            List<Map<String, AttributeValue>> items = wrapper.query(TABLE_NAME,
                    "RoomId = :rid AND SortKey < :sk",
                    null,
                    Map.of(
                            ":rid", AttributeValue.builder().s(chatId).build(),
                            ":sk", AttributeValue.builder().s(cursorSortKey).build()
                    ),
                    false, limit);

            List<Message> messages = items.stream().map(this::toMessage).collect(Collectors.toList());
            Collections.reverse(messages);
            return messages;
        } else {
            // No cursor: get latest N messages
            List<Map<String, AttributeValue>> items = wrapper.query(TABLE_NAME,
                    "RoomId = :rid",
                    null,
                    Map.of(":rid", AttributeValue.builder().s(chatId).build()),
                    false, limit);

            List<Message> messages = items.stream().map(this::toMessage).collect(Collectors.toList());
            Collections.reverse(messages);
            return messages;
        }
    }

    @Override
    public Optional<Message> findByMessageId(String messageId) {
        List<Map<String, AttributeValue>> items = wrapper.queryWithIndex(
                TABLE_NAME, GSI_MESSAGE_ID,
                "MessageId = :mid",
                null,
                Map.of(":mid", AttributeValue.builder().s(messageId).build()));

        if (items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toMessage(items.get(0)));
    }

    @Override
    public String sendMessage(Message message) {
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            message.setMessageId("msg" + UUID.randomUUID().toString().substring(0, 8));
        }
        if (message.getSentAt() == null) {
            message.setSentAt(LocalDateTime.now());
        }

        String sortKey = message.getSentAt().format(FMT) + "#" + message.getSenderUserId();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("RoomId", AttributeValue.builder().s(message.getChatId()).build());
        item.put("SortKey", AttributeValue.builder().s(sortKey).build());
        item.put("MessageId", AttributeValue.builder().s(message.getMessageId()).build());
        item.put("Message", AttributeValue.builder().s(message.getText()).build());
        item.put("SenderUserId", AttributeValue.builder().s(message.getSenderUserId()).build());

        if (message.getReadAt() != null) {
            item.put("ReadAt", AttributeValue.builder().s(message.getReadAt().format(FMT)).build());
        }

        wrapper.putItem(TABLE_NAME, item);
        return message.getMessageId();
    }

    @Override
    public void markAsRead(String messageId, String readerId) {
        Map<String, AttributeValue> key = findKeyByMessageId(messageId);
        if (key == null) return;

        wrapper.updateItem(TABLE_NAME, key,
                "SET ReadAt = :ra",
                null,
                Map.of(":ra", AttributeValue.builder().s(LocalDateTime.now().format(FMT)).build()));
    }

    @Override
    public void addReaction(String messageId, String userId, Integer reactionType) {
        Map<String, AttributeValue> key = findKeyByMessageId(messageId);
        if (key == null) return;

        String reactionKey = userId + "#" + reactionType;
        Map<String, AttributeValue> reactionValue = new HashMap<>();
        reactionValue.put("userId", AttributeValue.builder().s(userId).build());
        reactionValue.put("reactionType", AttributeValue.builder().n(String.valueOf(reactionType)).build());

        // Ensure Reactions map exists, then set the key
        wrapper.updateItem(TABLE_NAME, key,
                "SET Reactions = if_not_exists(Reactions, :emptyMap)",
                null,
                Map.of(":emptyMap", AttributeValue.builder().m(new HashMap<>()).build()));
        wrapper.updateItem(TABLE_NAME, key,
                "SET Reactions.#rkey = :rval",
                Map.of("#rkey", reactionKey),
                Map.of(":rval", AttributeValue.builder().m(reactionValue).build()));
    }

    @Override
    public void removeReaction(String messageId, String userId, Integer reactionType) {
        Map<String, AttributeValue> key = findKeyByMessageId(messageId);
        if (key == null) return;

        String reactionKey = userId + "#" + reactionType;

        try {
            wrapper.updateItem(TABLE_NAME, key,
                    "REMOVE Reactions.#rkey",
                    Map.of("#rkey", reactionKey),
                    null);
        } catch (Exception e) {
            // Ignore if Reactions or key doesn't exist
        }
    }

    private Map<String, AttributeValue> findKeyByMessageId(String messageId) {
        List<Map<String, AttributeValue>> items = wrapper.queryWithIndex(
                TABLE_NAME, GSI_MESSAGE_ID,
                "MessageId = :mid",
                null,
                Map.of(":mid", AttributeValue.builder().s(messageId).build()));

        if (items.isEmpty()) return null;

        Map<String, AttributeValue> item = items.get(0);
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("RoomId", item.get("RoomId"));
        key.put("SortKey", item.get("SortKey"));
        return key;
    }

    private Message toMessage(Map<String, AttributeValue> item) {
        Message msg = new Message();
        msg.setMessageId(item.get("MessageId").s());
        msg.setChatId(item.get("RoomId").s());
        msg.setSenderUserId(item.get("SenderUserId").s());
        msg.setText(item.get("Message").s());

        // Parse sentAt from SortKey (format: "yyyy-MM-dd'T'HH:mm:ss.SSS#userId")
        String sortKey = item.get("SortKey").s();
        String timestampPart = sortKey.substring(0, sortKey.lastIndexOf('#'));
        msg.setSentAt(LocalDateTime.parse(timestampPart, FMT));

        if (item.containsKey("ReadAt")) {
            msg.setReadAt(LocalDateTime.parse(item.get("ReadAt").s(), FMT));
        }

        // Parse reactions
        msg.setReactions(parseReactions(item));

        return msg;
    }

    private List<MessageReaction> parseReactions(Map<String, AttributeValue> item) {
        if (!item.containsKey("Reactions") || item.get("Reactions").m() == null || item.get("Reactions").m().isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, AttributeValue> reactionsMap = item.get("Reactions").m();

        // Aggregate by reactionType
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Map.Entry<String, AttributeValue> entry : reactionsMap.entrySet()) {
            Map<String, AttributeValue> reactionDetail = entry.getValue().m();
            int reactionType = Integer.parseInt(reactionDetail.get("reactionType").n());
            countMap.merge(reactionType, 1, Integer::sum);
        }

        return countMap.entrySet().stream()
                .map(e -> new MessageReaction(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
