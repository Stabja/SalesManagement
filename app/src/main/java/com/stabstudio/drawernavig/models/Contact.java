package com.stabstudio.drawernavig.models;


public class Contact {

    private String id;
    private String photoUrl;
    private String firstName;
    private String lastName;
    private String accountName;                 //From Accounts
    private String leadSource;
    private String department;
    private String dateofBirth;
    private String email;
    private String mobile;
    private String homePhone;
    private String officePhone;
    private String skypeId;
    private String linkedin;
    private String twitter;
    private String facebook;

    public Contact(){
    }

    public Contact(String id, String photoUrl, String firstName, String lastName, String accountName, String leadSource, String department, String dateofBirth, String email, String mobile, String homePhone, String officePhone, String skypeId, String linkedin, String twitter, String facebook) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountName = accountName;
        this.leadSource = leadSource;
        this.department = department;
        this.dateofBirth = dateofBirth;
        this.email = email;
        this.mobile = mobile;
        this.homePhone = homePhone;
        this.officePhone = officePhone;
        this.skypeId = skypeId;
        this.linkedin = linkedin;
        this.twitter = twitter;
        this.facebook = facebook;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
