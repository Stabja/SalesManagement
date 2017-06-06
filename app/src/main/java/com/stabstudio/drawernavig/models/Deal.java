package com.stabstudio.drawernavig.models;

import org.joda.time.DateTime;

public class Deal {

    private String id;
    private String storeId;
    private String dealName;
    private String amount;
    private DateTime startDate;
    private DateTime deadline;
    private String dealStatus;

    public Deal(){
    }

    public Deal(String dealName, String dealStatus, DateTime deadline){
        this.dealName = dealName;
        this.dealStatus = dealStatus;
        this.deadline = deadline;
    }

    public Deal(String id, String storeId, String dealName, String amount, DateTime startDate, DateTime deadline, String dealStatus){
        this.id = id;
        this.storeId = storeId;
        this.dealName = dealName;
        this.amount = amount;
        this.startDate = startDate;
        this.deadline = deadline;
        this.dealStatus = dealStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }
}
