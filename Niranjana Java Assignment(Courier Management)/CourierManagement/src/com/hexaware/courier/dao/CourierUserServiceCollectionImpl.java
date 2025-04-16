package com.hexaware.courier.dao;

import java.util.ArrayList;
import java.util.List;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.CourierCompany;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.entity.Location;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;

public class CourierUserServiceCollectionImpl implements ICourierUserService {
    protected CourierCompany companyObj;

    public CourierUserServiceCollectionImpl() {
        this.companyObj = new CourierCompany("HexaCourier");
        initializeSampleData();
    }

    private void initializeSampleData() {
        Employee emp1 = new Employee(1, "John Doe", "john@hexacourier.com", "1234567890", "Manager", 50000);
        Employee emp2 = new Employee(2, "Jane Smith", "jane@hexacourier.com", "9876543210", "Delivery Staff", 30000);
        companyObj.getEmployeeDetails().add(emp1);
        companyObj.getEmployeeDetails().add(emp2);

        Location loc1 = new Location(1, "Main Office", "123 Main St, City");
        Location loc2 = new Location(2, "Warehouse", "456 Industrial Area, City");
        companyObj.getLocationDetails().add(loc1);
        companyObj.getLocationDetails().add(loc2);

        Courier courier1 = new Courier();
        courier1.setCourierID(1);
        courier1.setSenderName("Sender1");
        courier1.setSenderAddress("123 Sender St");
        courier1.setReceiverName("Receiver1");
        courier1.setReceiverAddress("456 Receiver Ave");
        courier1.setWeight(2.5);
        courier1.setStatus("Delivered");
        courier1.setTrackingNumber("TR1001");
        courier1.setEmployeeId(2);
        courier1.setSenderId(101);
        courier1.setReceiverId(201);
        courier1.setServiceId(301);
        companyObj.getCourierDetails().add(courier1);
    }

    @Override
    public String placeOrder(Courier courierObj) {
        companyObj.getCourierDetails().add(courierObj);
        return courierObj.getTrackingNumber();
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        for (Courier courier : companyObj.getCourierDetails()) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                return courier.getStatus();
            }
        }
        throw new TrackingNumberNotFoundException();
    }

    @Override
    public boolean cancelOrder(String trackingNumber) {
        for (Courier courier : companyObj.getCourierDetails()) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                courier.setStatus("Cancelled");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> assignedCouriers = new ArrayList<>();
        for (Courier courier : companyObj.getCourierDetails()) {
            if (courier.getEmployeeId() == courierStaffId) {
                assignedCouriers.add(courier);
            }
        }
        return assignedCouriers;
    }
}