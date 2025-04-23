package com.hexaware.courier.exception;

public class TrackingNumberNotFoundException extends Exception {
    public TrackingNumberNotFoundException() {
        super("Tracking number not found in the system");
    }

    public TrackingNumberNotFoundException(String message) {
        super(message);
    }
}