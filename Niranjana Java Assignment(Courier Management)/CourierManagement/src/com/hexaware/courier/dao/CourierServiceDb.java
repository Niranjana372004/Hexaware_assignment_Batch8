package com.hexaware.courier.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import com.hexaware.courier.util.DBConnUtil;

public class CourierServiceDb implements ICourierUserService, ICourierAdminService {
    
    @Override
    public String placeOrder(Courier courierObj) {
        String sql = "INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, " +
                   "Weight, Status, TrackingNumber, DeliveryDate, EmployeeID, SenderID, ReceiverID, ServiceID) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, courierObj.getSenderName());
            pstmt.setString(2, courierObj.getSenderAddress());
            pstmt.setString(3, courierObj.getReceiverName());
            pstmt.setString(4, courierObj.getReceiverAddress());
            pstmt.setDouble(5, courierObj.getWeight());
            pstmt.setString(6, courierObj.getStatus());
            pstmt.setString(7, courierObj.getTrackingNumber());
            pstmt.setDate(8, new java.sql.Date(courierObj.getDeliveryDate().getTime()));
            pstmt.setInt(9, courierObj.getEmployeeId());
            pstmt.setInt(10, courierObj.getSenderId());
            pstmt.setInt(11, courierObj.getReceiverId());
            pstmt.setInt(12, courierObj.getServiceId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return courierObj.getTrackingNumber();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        String sql = "SELECT Status FROM Courier WHERE TrackingNumber = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, trackingNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("Status");
            } else {
                throw new TrackingNumberNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TrackingNumberNotFoundException("Database error occurred");
        }
    }

    @Override
    public boolean cancelOrder(String trackingNumber) {
        String sql = "UPDATE Courier SET Status = 'Cancelled' WHERE TrackingNumber = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, trackingNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> couriers = new ArrayList<>();
        String sql = "SELECT * FROM Courier WHERE EmployeeID = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courierStaffId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Courier courier = new Courier();
                courier.setCourierID(rs.getInt("CourierID"));
                courier.setSenderName(rs.getString("SenderName"));
                courier.setSenderAddress(rs.getString("SenderAddress"));
                courier.setReceiverName(rs.getString("ReceiverName"));
                courier.setReceiverAddress(rs.getString("ReceiverAddress"));
                courier.setWeight(rs.getDouble("Weight"));
                courier.setStatus(rs.getString("Status"));
                courier.setTrackingNumber(rs.getString("TrackingNumber"));
                courier.setDeliveryDate(rs.getDate("DeliveryDate"));
                courier.setEmployeeId(rs.getInt("EmployeeID"));
                courier.setSenderId(rs.getInt("SenderID"));
                courier.setReceiverId(rs.getInt("ReceiverID"));
                courier.setServiceId(rs.getInt("ServiceID"));
                
                couriers.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return couriers;
    }

    @Override
    public int addCourierStaff(Employee obj) {
        // First get the next available EmployeeID
        int newEmployeeId = getNextEmployeeId();
        if (newEmployeeId == -1) {
            return -1;
        }
        
        String sql = "INSERT INTO Employee (EmployeeID, Name, Email, ContactNumber, Role, Salary) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newEmployeeId);
            pstmt.setString(2, obj.getEmployeeName());
            pstmt.setString(3, obj.getEmail());
            pstmt.setString(4, obj.getContactNumber());
            pstmt.setString(5, obj.getRole());
            pstmt.setDouble(6, obj.getSalary());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return newEmployeeId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getNextEmployeeId() {
        String sql = "SELECT MAX(EmployeeID) + 1 AS NextID FROM Employee";
        
        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("NextID");
            }
            return 1; // If no employees exist yet
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean updateCourierStatus(String trackingNumber, String newStatus) {
        String sql = "UPDATE Courier SET Status = ? WHERE TrackingNumber = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setString(2, trackingNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        
        try (Connection conn = DBConnUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getString("Name"));
                emp.setEmail(rs.getString("Email"));
                emp.setContactNumber(rs.getString("ContactNumber"));
                emp.setRole(rs.getString("Role"));
                emp.setSalary(rs.getDouble("Salary"));
                
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException {
        String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setEmployeeName(rs.getString("Name"));
                emp.setEmail(rs.getString("Email"));
                emp.setContactNumber(rs.getString("ContactNumber"));
                emp.setRole(rs.getString("Role"));
                emp.setSalary(rs.getDouble("Salary"));
                return emp;
            } else {
                throw new InvalidEmployeeIdException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidEmployeeIdException("Database error occurred");
        }
    }
}