package com.stabstudio.drawernavig.models;


import org.joda.time.LocalDate;

public class Task {

    private String id;
    private String userId;
    private String subject;
    private LocalDate dueDate;
    private String status;
    private String priority;

    public Task(){
    }

    public Task(String id, String userId, String subject, LocalDate dueDate, String status, String priority){
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
