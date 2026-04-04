package com.oversession.dao.dynamodb;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Map;

@Component
public class DynamoDbClientWrapper {

    private final DynamoDbClient client;

    public DynamoDbClientWrapper(DynamoDbClient client) {
        this.client = client;
    }

    public void putItem(String tableName, Map<String, AttributeValue> item) {
        client.putItem(PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build());
    }

    public Map<String, AttributeValue> getItem(String tableName, Map<String, AttributeValue> key) {
        GetItemResponse response = client.getItem(GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build());
        return response.hasItem() ? response.item() : null;
    }

    public void updateItem(String tableName, Map<String, AttributeValue> key,
                           String updateExpression,
                           Map<String, String> expressionAttributeNames,
                           Map<String, AttributeValue> expressionAttributeValues) {
        UpdateItemRequest.Builder builder = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .updateExpression(updateExpression);

        if (expressionAttributeNames != null && !expressionAttributeNames.isEmpty()) {
            builder.expressionAttributeNames(expressionAttributeNames);
        }
        if (expressionAttributeValues != null && !expressionAttributeValues.isEmpty()) {
            builder.expressionAttributeValues(expressionAttributeValues);
        }

        client.updateItem(builder.build());
    }

    public List<Map<String, AttributeValue>> query(String tableName,
                                                    String keyConditionExpression,
                                                    Map<String, String> expressionAttributeNames,
                                                    Map<String, AttributeValue> expressionAttributeValues,
                                                    boolean scanIndexForward,
                                                    Integer limit) {
        QueryRequest.Builder builder = QueryRequest.builder()
                .tableName(tableName)
                .keyConditionExpression(keyConditionExpression)
                .scanIndexForward(scanIndexForward);

        if (expressionAttributeNames != null && !expressionAttributeNames.isEmpty()) {
            builder.expressionAttributeNames(expressionAttributeNames);
        }
        if (expressionAttributeValues != null && !expressionAttributeValues.isEmpty()) {
            builder.expressionAttributeValues(expressionAttributeValues);
        }
        if (limit != null && limit > 0) {
            builder.limit(limit);
        }

        return client.query(builder.build()).items();
    }

    public List<Map<String, AttributeValue>> queryWithIndex(String tableName,
                                                             String indexName,
                                                             String keyConditionExpression,
                                                             Map<String, String> expressionAttributeNames,
                                                             Map<String, AttributeValue> expressionAttributeValues) {
        QueryRequest.Builder builder = QueryRequest.builder()
                .tableName(tableName)
                .indexName(indexName)
                .keyConditionExpression(keyConditionExpression);

        if (expressionAttributeNames != null && !expressionAttributeNames.isEmpty()) {
            builder.expressionAttributeNames(expressionAttributeNames);
        }
        if (expressionAttributeValues != null && !expressionAttributeValues.isEmpty()) {
            builder.expressionAttributeValues(expressionAttributeValues);
        }

        return client.query(builder.build()).items();
    }

    public void deleteItem(String tableName, Map<String, AttributeValue> key) {
        client.deleteItem(DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build());
    }

    public void createTableIfNotExists(CreateTableRequest request) {
        try {
            client.describeTable(DescribeTableRequest.builder()
                    .tableName(request.tableName())
                    .build());
        } catch (ResourceNotFoundException e) {
            client.createTable(request);
        }
    }
}
