package com.stabstudio.drawernavig.models;
import org.joda.time.DateTime;

import java.util.ArrayList;

public class Feed {

    private String id;
    private String userId;
    private String title;
    private String photoUrl;
    private DateTime timeStamp;
    private int likes;
    private ArrayList<Comment> comments = new ArrayList<>();

    public Feed(){
    }

    public Feed(String id, String userId, String title, String photoUrl, DateTime timeStamp, int likes){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.photoUrl = photoUrl;
        this.timeStamp = timeStamp;
        this.likes = likes;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}
