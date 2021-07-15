package com.myfeeds;

public class FollowRequest {


    private String userToFollow ;
    private boolean followOrUnfollow;


    public String getUserToFollow() {
        return userToFollow;
    }

    public boolean getFollowOrUnfollow() {
        return followOrUnfollow;
    }


    public void setUserToFollow(String inp) {
        userToFollow = inp;
    }

    public void setFollowOrUnfollow(boolean inp) {
        followOrUnfollow = inp;
    }

}
