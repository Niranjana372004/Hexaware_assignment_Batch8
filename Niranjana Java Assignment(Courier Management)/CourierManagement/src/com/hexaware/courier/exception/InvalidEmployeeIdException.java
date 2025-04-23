package com.hexaware.courier.exception;

public class InvalidEmployeeIdException extends Exception {
    public InvalidEmployeeIdException() {
        super("Invalid employee ID provided");
    }

    public InvalidEmployeeIdException(String message) {
        super(message);
    }
}