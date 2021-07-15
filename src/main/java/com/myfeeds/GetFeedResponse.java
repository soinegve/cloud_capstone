package com.myfeeds;

import java.util.List;

public class GetFeedResponse {


    private final List<Post>  posts;
	

	public GetFeedResponse(List<Post> posts) {
		this.posts = posts;
		
	}

	public List<Post> getPosts() {
		return this.posts;
	}


}