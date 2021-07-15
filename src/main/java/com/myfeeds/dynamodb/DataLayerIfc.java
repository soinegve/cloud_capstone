package com.myfeeds.dynamodb;

import java.util.List;

import com.myfeeds.Post;

public interface DataLayerIfc {

    public void follow(String fromUser, String toUser);

    public void unfollow(String fromUser, String toUser);

    public List<Post> getLatestPostsFromUsersFollowed(String user);

    public List<Post> getLatestPostsFromSpecificUserFollowed(String fromUser, String user);

    public void createPost(Post post);


}
