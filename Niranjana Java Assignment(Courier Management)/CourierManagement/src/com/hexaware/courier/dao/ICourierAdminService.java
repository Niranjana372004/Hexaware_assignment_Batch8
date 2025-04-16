package com.hexaware.courier.dao;

import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import java.util.List;

public interface ICourierAdminService {
    int addCourierStaff(Employee obj);
    boolean updateCourierStatus(String trackingNumber, String newStatus);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException;
}