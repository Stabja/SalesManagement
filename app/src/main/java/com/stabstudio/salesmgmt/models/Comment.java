package com.stabstudio.salesmgmt.models;


public class Comment {

    private String id;
    private String userId;
    private String text;

    public Comment(){
    }

    public Comment(String id, String userId, String text){
        this.id = id;
        this.userId = userId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
