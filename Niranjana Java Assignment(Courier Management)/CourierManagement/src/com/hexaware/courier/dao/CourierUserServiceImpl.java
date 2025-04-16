//package com.hexaware.courier.dao;
//
//import com.hexaware.courier.entity.Courier;
//import com.hexaware.courier.entity.CourierCompany;
//import com.hexaware.courier.exception.TrackingNumberNotFoundException;
//import com.hexaware.courier.entity.Employee;
//import com.hexaware.courier.entity.Location;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CourierUserServiceImpl implements ICourierUserService {
//    protected CourierCompany companyObj;
//
//    public CourierUserServiceImpl() {
//        this.companyObj = new CourierCompany("HexaCourier");
//        // Initialize with some sample data
//        initializeSampleData();
//    }
//
//    private void initializeSampleData() {
//        // Add sample employees
//        Employee emp1 = new Employee(1, "John Doe", "john@hexacourier.com", "1234567890", "Manager", 50000);
//        Employee emp2 = new Employee(2, "Jane Smith", "jane@hexacourier.com", "9876543210", "Delivery Staff", 30000);
//        companyObj.getEmployeeDetails().add(emp1);
//        companyObj.getEmployeeDetails().add(emp2);
//
//        // Add sample locations
//        Location loc1 = new Location(1, "Main Office", "123 Main St, City");
//        Location loc2 = new Location(2, "Warehouse", "456 Industrial Area, City");
//        companyObj.getLocationDetails().add(loc1);
//        companyObj.getLocationDetails().add(loc2);
//
//        // Add sample couriers
//        Courier courier1 = new Courier();
//        courier1.setCourierID(1);
//        courier1.setSenderName("Sender1");
//        courier1.setSenderAddress("123 Sender St");
//        courier1.setReceiverName("Receiver1");
//        courier1.setReceiverAddress("456 Receiver Ave");
//        courier1.setWeight(2.5);
//        courier1.setStatus("Delivered");
//        courier1.setUserId(101);
//        courier1.setEmployeeId(2);
//        companyObj.getCourierDetails().add(courier1);
//    }
//
//    @Override
//    public String placeOrder(Courier courierObj) {
//        companyObj.getCourierDetails().add(courierObj);
//        return courierObj.getTrackingNumber();
//    }
//
//    @Override
//    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
//        for (Courier courier : companyObj.getCourierDetails()) {
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                return courier.getStatus();
//            }
//        }
//        throw new TrackingNumberNotFoundException();
//    }
//
//    @Override
//    public boolean cancelOrder(String trackingNumber) {
//        for (Courier courier : companyObj.getCourierDetails()) {
//            if (courier.getTrackingNumber().equals(trackingNumber)) {
//                courier.setStatus("Cancelled");
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public List<Courier> getAssignedOrder(long courierStaffId) {
//        List<Courier> assignedCouriers = new ArrayList<>();
//        for (Courier courier : companyObj.getCourierDetails()) {
//            if (courier.getEmployeeId() == courierStaffId) {
//                assignedCouriers.add(courier);
//            }
//        }
//        return assignedCouriers;
//    }
//}

package com.hexaware.courier.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import com.hexaware.courier.util.DBConnUtil;

public class CourierUserServiceImpl implements ICourierUserService {

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
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> assignedCouriers = new ArrayList<>();
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
                
                assignedCouriers.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignedCouriers;
    }
}