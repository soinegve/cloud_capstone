package com.myfeeds;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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


public class GetFeedsHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(GetFeedsHandler.class);



	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		LOG.info("received: {}", event);




		

		
		// if( !input.containsKey(userToFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value userToFollow");

		// if( !input.containsKey(shouldFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value shouldFollow");



		String user = "Karolos";

		DataLayerIfc dataLayer = new DynamoDbUtil();

	


	

		List<Post> feeds = dataLayer.getLatestPostsFromUsersFollowed(user);
		GetFeedResponse responseBody = new GetFeedResponse(feeds);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
