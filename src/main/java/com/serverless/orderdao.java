package com.serverless;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import connection.DynamoDBAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class orderdao {
    private final DynamoDBMapper mapper;

    public orderdao() {
        this.mapper = DynamoDBAdapter.getInstance()
                .createDbMapper(DynamoDBMapperConfig.builder().build());
    }

    public void insert(ordermodel order) {
        mapper.save(order);
    }

    public void update(ordermodel order, String id) {
        mapper.save(order, buildExpression(id));
    }

    private DynamoDBSaveExpression buildExpression(String id) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id",
                new ExpectedAttributeValue(new AttributeValue().withS(id)));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }

    public void delete(ordermodel order) {
        mapper.delete(order);
    }

    public List<ordermodel> findAll () {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<ordermodel> results = this.mapper.scan(ordermodel.class, scanExp);
        return results;
    }
}
