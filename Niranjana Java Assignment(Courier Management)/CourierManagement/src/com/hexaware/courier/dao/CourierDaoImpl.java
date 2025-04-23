package com.hexaware.courier.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import com.hexaware.courier.util.DBConnUtil;

public class CourierDaoImpl implements CourierDao {
    
    @Override
    public String placeOrder(Courier courierObj) {
        String sql = "INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, " +
                   "Weight, Status, TrackingNumber, DeliveryDate, EmployeeID, SenderID, ReceiverID, ServiceID) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setCourierParameters(pstmt, courierObj);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return courierObj.getTrackingNumber();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
                throw new TrackingNumberNotFoundException("Tracking number not found: " + trackingNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TrackingNumberNotFoundException("Database error occurred while fetching status");
        }
    }

    @Override
    public boolean cancelOrder(String trackingNumber) {
        String sql = "UPDATE Courier SET Status = 'Cancelled' WHERE TrackingNumber = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, trackingNumber);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Courier> getAssignedOrders(int courierStaffId) {
        List<Courier> assignedCouriers = new ArrayList<>();
        String sql = "SELECT * FROM Courier WHERE EmployeeID = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courierStaffId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                assignedCouriers.add(extractCourierFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignedCouriers;
    }

    @Override
    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) " +
                   "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            setEmployeeParameters(pstmt, employee);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
                employees.add(extractEmployeeFromResultSet(rs));
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
                return extractEmployeeFromResultSet(rs);
            } else {
                throw new InvalidEmployeeIdException("Employee not found with ID: " + employeeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidEmployeeIdException("Database error occurred");
        }
    }

    @Override
    public boolean updateCourierStatus(String trackingNumber, String newStatus) {
        String sql = "UPDATE Courier SET Status = ? WHERE TrackingNumber = ?";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setString(2, trackingNumber);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper methods
    private void setCourierParameters(PreparedStatement pstmt, Courier courier) throws SQLException {
        pstmt.setString(1, courier.getSenderName());
        pstmt.setString(2, courier.getSenderAddress());
        pstmt.setString(3, courier.getReceiverName());
        pstmt.setString(4, courier.getReceiverAddress());
        pstmt.setDouble(5, courier.getWeight());
        pstmt.setString(6, courier.getStatus());
        pstmt.setString(7, courier.getTrackingNumber());
        pstmt.setDate(8, new java.sql.Date(courier.getDeliveryDate().getTime()));
        pstmt.setInt(9, courier.getEmployeeId());
        pstmt.setInt(10, courier.getSenderId());
        pstmt.setInt(11, courier.getReceiverId());
        pstmt.setInt(12, courier.getServiceId());
    }

    private Courier extractCourierFromResultSet(ResultSet rs) throws SQLException {
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
        return courier;
    }

    private void setEmployeeParameters(PreparedStatement pstmt, Employee employee) throws SQLException {
        pstmt.setString(1, employee.getEmployeeName());
        pstmt.setString(2, employee.getEmail());
        pstmt.setString(3, employee.getContactNumber());
        pstmt.setString(4, employee.getRole());
        pstmt.setDouble(5, employee.getSalary());
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmployeeID(rs.getLong("EmployeeID"));
        emp.setEmployeeName(rs.getString("Name"));
        emp.setEmail(rs.getString("Email"));
        emp.setContactNumber(rs.getString("ContactNumber"));
        emp.setRole(rs.getString("Role"));
        emp.setSalary(rs.getDouble("Salary"));
        return emp;
    }
}