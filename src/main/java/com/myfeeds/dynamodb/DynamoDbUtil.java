package com.myfeeds.dynamodb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.myfeeds.Post;

public class DynamoDbUtil implements DataLayerIfc {


    private static final String FOLLOWS_TABLE_NAME = System.getenv("FOLLOWS_TABLE");
    private static final String POSTS_TABLE_NAME = System.getenv("POSTS_TABLE");

    private static final Logger LOG = LogManager.getLogger(DynamoDbUtil.class);


    final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

    public void follow(String fromUser, String toUser) {
        Map<String, AttributeValue> item = new HashMap<>();

        item.put("followerId", new AttributeValue(fromUser));
        item.put("followeeId", new AttributeValue(toUser));

		PutItemRequest putItemRequest = new PutItemRequest()
        .withTableName(FOLLOWS_TABLE_NAME)
        .withItem(item);
        
        

        ;
        ddb.putItem(putItemRequest);

    }

    
    public void unfollow(String fromUser, String toUser) {


        Map<String, AttributeValue> item = new HashMap<>();

        item.put("followerId", new AttributeValue(fromUser));
        item.put("followeeId", new AttributeValue(toUser));


        DeleteItemRequest deleteItemRequest = 
            new DeleteItemRequest()
            .withTableName(FOLLOWS_TABLE_NAME)
            .withKey(item);
            ddb.deleteItem(deleteItemRequest);
    }


    @Override
    public List<Post> getLatestPostsFromUsersFollowed(String user) {
    


        Map<String, AttributeValue> expressionAttributeValuesFollows = new HashMap<>();
       
		expressionAttributeValuesFollows.put(":v_id", new AttributeValue().withS(user));
        List<String> usersFollowed = new ArrayList<>();

    
        QueryRequest queryRequestFollows =  new QueryRequest()
       
        .withTableName(FOLLOWS_TABLE_NAME)
        .withKeyConditionExpression("followerId = :v_id")
        .withExpressionAttributeValues(expressionAttributeValuesFollows);
		QueryResult resultFollows = ddb.query(queryRequestFollows);

        for(Map<String,AttributeValue> r : resultFollows.getItems())
        {
            usersFollowed.add(r.get("followeeId").getS());
        }


        LOG.info("Users followed by " + user + " are " + usersFollowed.size());

        for(String u : usersFollowed)
            LOG.info("User--> " + u);



        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
       
		
        
        List<Post> toReturn = new ArrayList<>();

        for(String u : usersFollowed){

            expressionAttributeValues.put(":userId", new AttributeValue().withS(u));

            QueryRequest queryRequest = new QueryRequest()
            .withLimit(10)
            .withTableName(POSTS_TABLE_NAME)
            .withKeyConditionExpression("userId = :userId")
            .withExpressionAttributeValues(expressionAttributeValues);
            
            QueryResult result = ddb.query(queryRequest);


        
            LOG.info("Scan Retured " + result.getCount() + " matches");


            for(Map<String,AttributeValue> r : result.getItems())
            {
                toReturn.add(new Post(
                    r.get("postId").getS()
                    , r.get("userId").getS()
                    , r.get("content").getS() 
                    , r.get("imageUrl").getS()));
            }
        }


        return toReturn;
    }


    @Override
    public List<Post> getLatestPostsFromSpecificUserFollowed(String fromUser, String user) {
        // TODO Auto-generated method stub
        return null;
    }


	@Override
	public void createPost(Post post) {


        Map<String, AttributeValue> item = new HashMap<>();

        item.put("postId", new AttributeValue(post.getPostId()));
        item.put("userId", new AttributeValue(post.getUserId()));
        item.put("content", new AttributeValue(post.getContent()));


        if(post.getImageUrl()!=null)
            item.put("imageUrl", new AttributeValue(post.getImageUrl()));

		PutItemRequest putItemRequest = new PutItemRequest()
        .withTableName(POSTS_TABLE_NAME)
        .withItem(item);
        
        

        ;
        ddb.putItem(putItemRequest);
		
	}
    
}
