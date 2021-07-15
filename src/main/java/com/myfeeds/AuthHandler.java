package com.myfeeds;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.myfeeds.dynamodb.DataLayerIfc;
import com.myfeeds.dynamodb.DynamoDbUtil;


public class AuthHandler implements RequestHandler<Map<String, Object>, AuthPolicy> {

	private static final Logger LOG = LogManager.getLogger(FollowHandler.class);

	private static final String userToFollowTokenName = "userToFollow";
	private static final String shouldFollowTokenName = "shouldFollow";


	@Override
	public AuthPolicy handleRequest(Map<String, Object> input, Context context) {
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

		// String message = "all good";
		// Response responseBody = new Response(message, input);
		// return ApiGatewayResponse.builder()
		// 		.setStatusCode(200)
		// 		.setObjectBody(responseBody)
		// 		.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
		// 		.build();


	// 	Policy policy = new Policy("MyQueuePolicy");

	// 	String principalId = "BAL";
	// 	policy.withStatements(new Statement(Effect.Allow)
    //    .withPrincipals(new Principal(principalId))
    //    .withActions(new ExecuteApiInvoke())
    //    .withResources(new Resource("*")));

	//    return policy;


				String principalId = "";
				String region = "";
				String awsAccountId = "";
				String restApiId = "";
				String stage = "";
				return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
	}


	public class ExecuteApiInvoke implements Action{

		@Override
		public String getActionName() {
			// TODO Auto-generated method stub
			return "execute-api:Invoke";
		}


	}
}
