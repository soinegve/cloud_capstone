package com.myfeeds;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthUtils {


    public static String extractToken(String token)
    {


        String[] tokenComps = token.split(" ");
		
		if(tokenComps.length!=2)
			throw new RuntimeException("Invalid authorization key format");

		if(!tokenComps[0].toLowerCase().equals("bearer"))
			throw new RuntimeException("Invalid authorization key format : bearer must be first part");

    
        return tokenComps[1];
    }
    

    public static String extractUserId(String token)
    {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }


	public static String extractUserFromToken(String string) {
		return extractUserId(extractToken(string));
	}
}
