package com.myfeeds;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GetPresigneUrlHandler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(GetPresigneUrlHandler.class);



	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		LOG.info("received: {}", event);



// This is usually done at application startup, because creating a presigner can be expensive.
		// S3Presigner presigner = S3Presigner.create();

		// // Create a GetObjectRequest to be pre-signed
		// GetObjectRequest getObjectRequest =
		// 		GetObjectRequest.builder()
		// 						.bucket("my-bucket")
		// 						.key("my-key")
		// 						.build();

		// // Create a GetObjectPresignRequest to specify the signature duration
		// GetObjectPresignRequest getObjectPresignRequest =
		// 	GetObjectPresignRequest.builder()
		// 						.signatureDuration(Duration.ofMinutes(10))
		// 						.getObjectRequest(getObjectRequest)
		// 						.build();

		// // Generate the presigned request
		// PresignedGetObjectRequest presignedGetObjectRequest =
		// 	presigner.presignGetObject(getObjectPresignRequest);

		// Log the presigned URL, for example.
		// System.out.println("Presigned URL: " + presignedGetObjectRequest.url());


		

		
		// if( !input.containsKey(userToFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value userToFollow");

		// if( !input.containsKey(shouldFollowTokenName))
		//    throw new RuntimeException("Missing mandatory value shouldFollow");



		String user = "Karolos";



		Regions clientRegion = Regions.DEFAULT_REGION;
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = Instant.now().toEpochMilli();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            String bucketName = "";
			String objectKey =  "";
			GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            java.net.URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
	


	

		Object responseBody = null;
		// List<Post> feeds = dataLayer.getLatestPostsFromUsersFollowed(user);
		// GetFeedResponse responseBody = new GetFeedResponse(feeds);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
