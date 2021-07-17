package com.myfeeds;

import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;



public class AuthHandler implements RequestHandler<Map<String, Object>, AuthPolicy> {

	private static final Logger LOG = LogManager.getLogger(FollowHandler.class);




	@Override
	public AuthPolicy handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);

		String token  = input.get("authorizationToken").toString();


		token = AuthUtils.extractToken(token);
        final JwkProvider jwkProvider = new UrlJwkProvider("https://dev-6co6-fbx.us.auth0.com");

        try {
            DecodedJWT jwt = JWT.decode(token);

            Jwk boom = jwkProvider.get(jwt.getKeyId());
          
        } catch (JWTDecodeException exception){
			throw new RuntimeException("JWTDecodeException");
        } catch (JwkException e) {
			throw new RuntimeException("JwkException");
		}
		
		String principalId = "";
		String region = "";
		String awsAccountId = "";
		String restApiId = "";
		String stage = "";
		return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
	}


}
