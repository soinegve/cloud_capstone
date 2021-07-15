package com.myfeeds;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.myfeeds.dynamodb.DataLayerIfc;
import com.myfeeds.dynamodb.DynamoDbUtil;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FollowHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(FollowHandler.class);



	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		LOG.info("received: {}", event);

		String body = event.getBody();



		FollowRequest followRequest = null;
		try {
			followRequest = new ObjectMapper().readValue(body, FollowRequest.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Not able to parse request");
		}  

		
		// if( !input.containsKey(userToFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value userToFollow");

		// if( !input.containsKey(shouldFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value shouldFollow");


		boolean shouldFollow = followRequest.getFollowOrUnfollow();;   

		String fromUser = "Karolos";
		String toUser = followRequest.getUserToFollow();

		DataLayerIfc dataLayer = new DynamoDbUtil();

		if(shouldFollow)
			dataLayer.follow(fromUser,toUser);
		else	
			dataLayer.unfollow(fromUser,toUser);

	
		String message = shouldFollow ? "User succesfully followed" : "User succesfully unfollowed"; 

		Map<String, Object> input = new HashMap<>();
		Response responseBody = new Response(message, input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
