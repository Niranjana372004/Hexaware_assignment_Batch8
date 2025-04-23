package com.hexaware.courier.service;

import com.hexaware.courier.dao.CourierDao;
import com.hexaware.courier.dao.CourierDaoImpl;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

public class CourierServiceImpl implements CourierUserService, CourierAdminService {
    
    private final CourierDao courierDao;
    
    public CourierServiceImpl() {
        this.courierDao = new CourierDaoImpl();
    }
    
    public CourierServiceImpl(CourierDao courierDao) {
        this.courierDao = courierDao;
    }

    // CourierUserService implementations
    @Override
    public String placeOrder(Courier courierObj) throws IllegalArgumentException {
        validateCourier(courierObj);
        return courierDao.placeOrder(courierObj);
    }

    @Override
    public String getOrderStatus(String trackingNumber) 
            throws TrackingNumberNotFoundException, IllegalArgumentException {
        validateTrackingNumber(trackingNumber);
        return courierDao.getOrderStatus(trackingNumber);
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws IllegalArgumentException {
        validateTrackingNumber(trackingNumber);
        return courierDao.cancelOrder(trackingNumber);
    }

    @Override
    public List<Courier> getAssignedOrders(int courierStaffId) throws IllegalArgumentException {
        if (courierStaffId <= 0) {
            throw new IllegalArgumentException("Courier staff ID must be positive");
        }
        return courierDao.getAssignedOrders(courierStaffId);
    }

    // CourierAdminService implementations
    @Override
    public int addCourierStaff(Employee employee) throws IllegalArgumentException {
        validateEmployee(employee);
        return courierDao.addEmployee(employee);
    }

    @Override
    public boolean updateCourierStatus(String trackingNumber, String newStatus) throws IllegalArgumentException {
        validateTrackingNumber(trackingNumber);
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        return courierDao.updateCourierStatus(trackingNumber, newStatus);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return courierDao.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int employeeId) 
            throws InvalidEmployeeIdException, IllegalArgumentException {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
        return courierDao.getEmployeeById(employeeId);
    }

    // Validation methods
    private void validateCourier(Courier courier) throws IllegalArgumentException {
        if (courier == null) {
            throw new IllegalArgumentException("Courier object cannot be null");
        }
        
        if (courier.getSenderName() == null || courier.getSenderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender name cannot be empty");
        }
        
        if (courier.getReceiverName() == null || courier.getReceiverName().trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver name cannot be empty");
        }
        
        if (courier.getWeight() <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        
        validateTrackingNumber(courier.getTrackingNumber());
        
        if (courier.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
    }

    private void validateTrackingNumber(String trackingNumber) throws IllegalArgumentException {
        if (trackingNumber == null || trackingNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Tracking number cannot be empty");
        }
        
        if (!Pattern.matches("^[A-Za-z0-9]{6,20}$", trackingNumber)) {
            throw new IllegalArgumentException("Tracking number must be 6-20 alphanumeric characters");
        }
    }

    private void validateEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee object cannot be null");
        }
        
        if (employee.getEmployeeName() == null || employee.getEmployeeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        
        if (employee.getEmail() == null || !Pattern.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", employee.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (employee.getContactNumber() == null || !Pattern.matches("^[0-9]{10,15}$", employee.getContactNumber())) {
            throw new IllegalArgumentException("Contact number must be 10-15 digits");
        }
        
        if (employee.getRole() == null || employee.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        
        if (employee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
    }
}