package com.myfeeds;


import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.myfeeds.dynamodb.DataLayerIfc;
import com.myfeeds.dynamodb.DynamoDbUtil;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PublishHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(FollowHandler.class);



	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
    

        LOG.info("received: {}", event);

		String body = event.getBody();



		PostRequest postRequest = null;
		try {
			postRequest = new ObjectMapper().readValue(body, PostRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Not able to parse request");
		}  

	


		String text = postRequest.getContent();   
        String imageUrl = postRequest.getImageUrl();

        String userId =  AuthUtils.extractUserFromToken(event.getHeaders().get("Authorization"));
        

        String postId = UUID.randomUUID().toString();

        Post post = new Post(postId,userId,text,imageUrl);
       

        
		DataLayerIfc dataLayer = new DynamoDbUtil();

        dataLayer.createPost(post);
	
		

		Map<String, Object> input = new HashMap<>();
		Response responseBody = new Response("all good", input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();

    }
    
}
