package com.hexaware.courier.entity;

import java.util.ArrayList;
import java.util.List;

public class CourierCompanyCollection {
    private String companyName;
    private List<Courier> courierDetails = new ArrayList<>();
    private List<Employee> employeeDetails = new ArrayList<>();
    private List<Location> locationDetails = new ArrayList<>();
    private List<User> userDetails = new ArrayList<>();
    private List<Payment> paymentDetails = new ArrayList<>();

    // Getters and setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Courier> getCourierDetails() {
        return courierDetails;
    }

    public List<Employee> getEmployeeDetails() {
        return employeeDetails;
    }

    public List<Location> getLocationDetails() {
        return locationDetails;
    }

    public List<User> getUserDetails() {
        return userDetails;
    }

    public List<Payment> getPaymentDetails() {
        return paymentDetails;
    }
}