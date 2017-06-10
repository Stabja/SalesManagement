package com.stabstudio.salesmgmt.models;

public class Deal {

    private String id;
    private String dealOwner;
    private String amount;
    private String dealName;
    private String deadLine;        //Date
    private String accountName;     //List from Accounts
    private String stage;           //List
    private String type;            //List
    private String probability;
    private String nextStep;
    private String leadSource;      //List
    private String contactName;     //List from Contacts

    private Deal(){
    }

    public Deal(String id, String dealOwner, String amount, String dealName, String deadLine, String accountName, String stage, String type, String probability, String nextStep, String leadSource, String contactName) {
        this.id = id;
        this.dealOwner = dealOwner;
        this.amount = amount;
        this.dealName = dealName;
        this.deadLine = deadLine;
        this.accountName = accountName;
        this.stage = stage;
        this.type = type;
        this.probability = probability;
        this.nextStep = nextStep;
        this.leadSource = leadSource;
        this.contactName = contactName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealOwner() {
        return dealOwner;
    }

    public void setDealOwner(String dealOwner) {
        this.dealOwner = dealOwner;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
