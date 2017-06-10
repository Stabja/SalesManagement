package com.stabstudio.salesmgmt.models;


import java.util.ArrayList;

public class Event {

    private String id;
    private String title;
    private String location;
    private Boolean allDay;
    private String from;           //Picker
    private String to;             //Picker
    private String organiser;      //List from Accounts
    private ArrayList<String> participants;

    public Event(){
    }

    public Event(String id, String title, String location, Boolean allDay, String from, String to, String organiser, ArrayList<String> participants) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.allDay = allDay;
        this.from = from;
        this.to = to;
        this.organiser = organiser;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

}
