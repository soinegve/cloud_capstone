package com.myfeeds;

public class Post {




    private String postId;
	private String userId;
	private String content;
	private String imageUrl;

	public Post(String postId, String userId, String text, String imageUrl) {

        this.setPostId(postId);
        this.setUserId(userId);
        this.setContent(text);
        this.setImageUrl(imageUrl);
    }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

   

}
