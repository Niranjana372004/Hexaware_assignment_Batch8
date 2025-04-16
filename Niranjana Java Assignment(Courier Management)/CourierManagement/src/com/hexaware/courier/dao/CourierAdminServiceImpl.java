//package com.hexaware.courier.dao;
//
//import com.hexaware.courier.entity.Courier;
//import com.hexaware.courier.entity.Employee;
//import com.hexaware.courier.exception.InvalidEmployeeIdException;
//import java.util.List;
//
//public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService {
//    @Override
//    public int addCourierStaff(Employee obj) {
//        // Generate new employee ID
//        int newId = companyObj.getEmployeeDetails().size() + 1;
//        obj.setEmployeeID(newId);
//        companyObj.getEmployeeDetails().add(obj);
//        return newId;
//    }
//
//    @Override
//    public boolean updateCourierStatus(String trackingNumber, String newStatus) {
//        for (Courier courier : companyObj.getCourierDetails()) {
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                courier.setStatus(newStatus);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public List<Employee> getAllEmployees() {
//        return companyObj.getEmployeeDetails();
//    }
//
//    @Override
//    public Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException {
//        for (Employee emp : companyObj.getEmployeeDetails()) {
//            if (emp.getEmployeeID() == employeeId) {
//                return emp;
//            }
//        }
//        throw new InvalidEmployeeIdException();
//    }
//}

package com.hexaware.courier.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;
import com.hexaware.courier.util.DBConnUtil;

public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService {

    @Override
    public int addCourierStaff(Employee obj) {
        String sql = "INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) " +
                   "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, obj.getEmployeeName());
            pstmt.setString(2, obj.getEmail());
            pstmt.setString(3, obj.getContactNumber());
            pstmt.setString(4, obj.getRole());
            pstmt.setDouble(5, obj.getSalary());
            
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
                Employee emp = new Employee();
                emp.setEmployeeID(rs.getLong("EmployeeID"));
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
                emp.setEmployeeID(rs.getLong("EmployeeID"));
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
}