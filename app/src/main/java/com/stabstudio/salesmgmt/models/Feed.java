package com.stabstudio.salesmgmt.models;


public class Feed {

    private String id;
    private String userId;         //From Accounts
    private String title;
    private String status;
    private String photoUrl;
    private String timeStamp;
    private int likes;
    private int comments;

    public Feed(){
    }

    public Feed(String id, String userId, String title, String status, String photoUrl, String timeStamp, int likes, int comments) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.status = status;
        this.photoUrl = photoUrl;
        this.timeStamp = timeStamp;
        this.likes = likes;
        this.comments = comments;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

}
