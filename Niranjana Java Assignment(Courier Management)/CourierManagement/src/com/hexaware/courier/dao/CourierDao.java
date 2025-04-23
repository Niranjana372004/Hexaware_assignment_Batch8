package com.hexaware.courier.dao;

import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import java.util.List;

public interface CourierDao {
    // Courier operations
    String placeOrder(Courier courierObj);
    String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException;
    boolean cancelOrder(String trackingNumber);
    List<Courier> getAssignedOrders(int courierStaffId);
    boolean updateCourierStatus(String trackingNumber, String newStatus);
    
    // Employee operations
    int addEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException;
}