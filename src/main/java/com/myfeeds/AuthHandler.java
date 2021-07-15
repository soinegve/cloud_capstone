package com.myfeeds;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.myfeeds.dynamodb.DataLayerIfc;
import com.myfeeds.dynamodb.DynamoDbUtil;


public class AuthHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(FollowHandler.class);

	private static final String userToFollowTokenName = "userToFollow";
	private static final String shouldFollowTokenName = "shouldFollow";


	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);

		
		// if ( true)
		// 		throw new RuntimeException("Noy authorised");
		// if( !input.containsKey(userToFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value userToFollow");

		// if( !input.containsKey(shouldFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value shouldFollow");


		// boolean shouldFollow = Boolean.parseBoolean(input.get(shouldFollowTokenName).toString());   

		// String fromUser = "";
		// String toUser = input.get(userToFollowTokenName).toString();

		// DataLayerIfc dataLayer = new DynamoDbUtil();

		// if(shouldFollow)
		// 	dataLayer.follow(fromUser,toUser);
		// else	
		// 	dataLayer.unfollow(fromUser,toUser);

	
		// String message = shouldFollow ? "User succesfully followed" : "User succesfully unfollowed"; 

		String message = "all good";
		Response responseBody = new Response(message, input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
