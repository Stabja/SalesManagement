package com.stabstudio.drawernavig.models;

import org.joda.time.DateTime;

public class Event {

    private String id;
    private String title;
    private String location;
    private Boolean allDay;
    private DateTime from;
    private DateTime to;
    private String organiserId;

    public Event(){
    }

    public Event(String id, String title, String location, Boolean allDay, DateTime from, DateTime to, String organiserId){
        this.id = id;
        this.title = title;
        this.location = location;
        this.allDay = allDay;
        this.from = from;
        this.to = to;
        this.organiserId = organiserId;
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

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(DateTime from) {
        this.from = from;
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(DateTime to) {
        this.to = to;
    }

    public String getOrganiserId() {
        return organiserId;
    }

    public void setOrganiserId(String organiserId) {
        this.organiserId = organiserId;
    }

}
