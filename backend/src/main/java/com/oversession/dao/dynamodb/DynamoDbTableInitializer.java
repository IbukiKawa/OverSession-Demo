package com.oversession.dao.dynamodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamoDbTableInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DynamoDbTableInitializer.class);
    private static final String TABLE_NAME = "Messages";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final DynamoDbClientWrapper wrapper;

    public DynamoDbTableInitializer(DynamoDbClientWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void run(String... args) {
        createMessagesTable();
        insertSeedData();
    }

    private void createMessagesTable() {
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(
                        KeySchemaElement.builder().attributeName("RoomId").keyType(KeyType.HASH).build(),
                        KeySchemaElement.builder().attributeName("SortKey").keyType(KeyType.RANGE).build()
                )
                .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("RoomId").attributeType(ScalarAttributeType.S).build(),
                        AttributeDefinition.builder().attributeName("SortKey").attributeType(ScalarAttributeType.S).build(),
                        AttributeDefinition.builder().attributeName("MessageId").attributeType(ScalarAttributeType.S).build()
                )
                .globalSecondaryIndexes(
                        GlobalSecondaryIndex.builder()
                                .indexName("MessageIdIndex")
                                .keySchema(
                                        KeySchemaElement.builder().attributeName("MessageId").keyType(KeyType.HASH).build()
                                )
                                .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                                .provisionedThroughput(ProvisionedThroughput.builder()
                                        .readCapacityUnits(5L).writeCapacityUnits(5L).build())
                                .build()
                )
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L).writeCapacityUnits(5L).build())
                .build();

        wrapper.createTableIfNotExists(request);
        log.info("DynamoDB Messages table ready");
    }

    private void insertSeedData() {
        // Check if data already exists
        var existing = wrapper.query(TABLE_NAME, "RoomId = :rid",
                null,
                Map.of(":rid", AttributeValue.builder().s("chat01").build()),
                true, 1);
        if (!existing.isEmpty()) {
            log.info("Seed data already exists, skipping");
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        // chat01 messages
        putMessage("chat01", "msg001", "user01", "こんにちは！マッチングしましたね。",
                now.minusMinutes(30), now.minusMinutes(28));
        putMessage("chat01", "msg002", "user02", "こんにちは！よろしくお願いします。",
                now.minusMinutes(25), now.minusMinutes(24));
        putMessage("chat01", "msg003", "user01", "今日ランチでもどうですか？",
                now.minusMinutes(20), now.minusMinutes(18));
        putMessage("chat01", "msg004", "user02", "いいですね！12時でどうでしょう？",
                now.minusMinutes(15), now.minusMinutes(14));
        putMessage("chat01", "msg005", "user01", "12時で大丈夫です。",
                now.minusMinutes(12), now.minusMinutes(11));
        putMessage("chat01", "msg006", "user02", "よろしくお願いします！",
                now.minusMinutes(10), null);
        putMessage("chat01", "msg007", "user02", "明日もよろしくお願いします！",
                now.minusMinutes(9), null);

        // chat02 messages
        putMessage("chat02", "msg008", "user03", "はじめまして！",
                now.minusHours(48), now.minusHours(47).minusMinutes(30));
        putMessage("chat02", "msg009", "user01", "はじめまして！よろしくお願いします。",
                now.minusHours(47), now.minusHours(46));
        putMessage("chat02", "msg010", "user03", "ありがとうございました",
                now.minusHours(24), now.minusHours(23).minusMinutes(30));

        // Reaction on msg003: user02 type=1
        addReactionToSeed("chat01", "msg003", "user02", 1);

        log.info("Seed data inserted into DynamoDB Messages table");
    }

    private void putMessage(String chatId, String messageId, String senderUserId,
                            String text, LocalDateTime sentAt, LocalDateTime readAt) {
        String sortKey = sentAt.format(FMT) + "#" + senderUserId;

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("RoomId", AttributeValue.builder().s(chatId).build());
        item.put("SortKey", AttributeValue.builder().s(sortKey).build());
        item.put("MessageId", AttributeValue.builder().s(messageId).build());
        item.put("Message", AttributeValue.builder().s(text).build());
        item.put("SenderUserId", AttributeValue.builder().s(senderUserId).build());

        if (readAt != null) {
            item.put("ReadAt", AttributeValue.builder().s(readAt.format(FMT)).build());
        }

        wrapper.putItem(TABLE_NAME, item);
    }

    private void addReactionToSeed(String chatId, String messageId, String userId, int reactionType) {
        // Find the message via GSI
        var items = wrapper.queryWithIndex(TABLE_NAME, "MessageIdIndex",
                "MessageId = :mid",
                null,
                Map.of(":mid", AttributeValue.builder().s(messageId).build()));

        if (items.isEmpty()) return;

        var item = items.get(0);
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("RoomId", item.get("RoomId"));
        key.put("SortKey", item.get("SortKey"));

        // Ensure Reactions map exists first
        wrapper.updateItem(TABLE_NAME, key,
                "SET Reactions = if_not_exists(Reactions, :emptyMap)",
                null,
                Map.of(":emptyMap", AttributeValue.builder().m(new HashMap<>()).build()));

        String reactionKey = userId + "#" + reactionType;
        Map<String, AttributeValue> reactionValue = new HashMap<>();
        reactionValue.put("userId", AttributeValue.builder().s(userId).build());
        reactionValue.put("reactionType", AttributeValue.builder().n(String.valueOf(reactionType)).build());

        wrapper.updateItem(TABLE_NAME, key,
                "SET Reactions.#rkey = :rval",
                Map.of("#rkey", reactionKey),
                Map.of(":rval", AttributeValue.builder().m(reactionValue).build()));
    }
}
