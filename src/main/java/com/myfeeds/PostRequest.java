package com.myfeeds;

public class PostRequest {


    private String content;
    private String imageUrl;




    public void setContent(String inp) {
         content= inp;
    }

    public void setImageUrl(String inp) {
         imageUrl = inp;
    }


    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
