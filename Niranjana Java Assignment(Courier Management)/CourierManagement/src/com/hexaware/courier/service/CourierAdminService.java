package com.hexaware.courier.service;

import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import java.util.List;

public interface CourierAdminService {
    int addCourierStaff(Employee employee) throws IllegalArgumentException;
    boolean updateCourierStatus(String trackingNumber, String newStatus) 
            throws IllegalArgumentException;
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int employeeId) 
            throws InvalidEmployeeIdException, IllegalArgumentException;
}