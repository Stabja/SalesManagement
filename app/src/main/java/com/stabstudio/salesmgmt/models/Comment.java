package com.stabstudio.salesmgmt.models;


public class Comment {

    private String id;
    private String feedId;
    private String photoUrl;
    private String author;
    private String text;
    private String timestamp;

    public Comment(){
    }

    public Comment(String id, String feedId, String photoUrl, String author, String text, String timestamp) {
        this.id = id;
        this.feedId = feedId;
        this.photoUrl = photoUrl;
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
