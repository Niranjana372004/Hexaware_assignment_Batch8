package com.hexaware.career.entity;

public class Company {
    private int companyID;
    private String companyName;
    private String location;
    private String city;
    private String state;

    public Company() {
        super();
    }

    public Company(int companyID, String companyName, String location, String city, String state) {
        super();
        this.companyID = companyID;
        this.companyName = companyName;
        this.location = location;
        this.city = city;
        this.state = state;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Company [companyID=" + companyID + ", companyName=" + companyName +
               ", location=" + location + ", city=" + city + ", state=" + state + "]";
    }
}
