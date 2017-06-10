package com.stabstudio.salesmgmt.models;


public class Task {

    private String id;
    private String assigner;     //List from Accounts
    private String subject;      //List
    private String dueDate;      //Date
    private String assignee;     //List from Contacts
    private String status;       //List
    private String priority;     //List
    private Boolean notification;
    private String repeat;       //List

    public Task(){
    }

    public Task(String id, String assigner, String subject, String dueDate, String assignee, String status, String priority, Boolean notification, String repeat) {
        this.id = id;
        this.assigner = assigner;
        this.subject = subject;
        this.dueDate = dueDate;
        this.assignee = assignee;
        this.status = status;
        this.priority = priority;
        this.notification = notification;
        this.repeat = repeat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
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

    public Boolean isNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

}
