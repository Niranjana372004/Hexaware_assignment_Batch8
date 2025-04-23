package com.hexaware.courier.service;

import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.exception.TrackingNumberNotFoundException;
import java.util.List;

public interface CourierUserService {
    String placeOrder(Courier courierObj) throws IllegalArgumentException;
    String getOrderStatus(String trackingNumber) 
            throws TrackingNumberNotFoundException, IllegalArgumentException;
    boolean cancelOrder(String trackingNumber) throws IllegalArgumentException;
    List<Courier> getAssignedOrders(int courierStaffId) throws IllegalArgumentException;
}