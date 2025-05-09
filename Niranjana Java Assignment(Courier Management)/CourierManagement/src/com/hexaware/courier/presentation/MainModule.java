package com.hexaware.courier.presentation;

import com.hexaware.courier.service.CourierServiceImpl;
import com.hexaware.courier.service.CourierUserService;
import com.hexaware.courier.service.CourierAdminService;
import com.hexaware.courier.entity.*;
import com.hexaware.courier.exception.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static Scanner scanner = new Scanner(System.in);
    private static CourierUserService userService;
    private static CourierAdminService adminService;
    
    public static void main(String[] args) {
        try {
            // Initialize the service implementation
            CourierServiceImpl courierServiceImpl = new CourierServiceImpl();
            userService = courierServiceImpl; // Implements CourierUserService
            adminService = courierServiceImpl; // Implements CourierAdminService
            
            boolean running = true;
            while (running) {
                System.out.println("\nCourier Management System");
                System.out.println("1. User Menu");
                System.out.println("2. Admin Menu");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        userMenu();
                        break;
                    case 2:
                        adminMenu();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("Thank you for using HexaCourier System. Goodbye!");
        }
    }
    
    private static void userMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nUser Menu");
            System.out.println("1. Place Order");
            System.out.println("2. Check Order Status");
            System.out.println("3. Cancel Order");
            System.out.println("4. View Assigned Orders");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            try {
                switch (choice) {
                    case 1:
                        placeOrder();
                        break;
                    case 2:
                        checkOrderStatus();
                        break;
                    case 3:
                        cancelOrder();
                        break;
                    case 4:
                        viewAssignedOrders();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void placeOrder() {
        System.out.println("\nPlace New Order");
        
        Courier courier = new Courier();
        System.out.print("Enter sender name: ");
        courier.setSenderName(scanner.nextLine());
        
        System.out.print("Enter sender address: ");
        courier.setSenderAddress(scanner.nextLine());
        
        System.out.print("Enter receiver name: ");
        courier.setReceiverName(scanner.nextLine());
        
        System.out.print("Enter receiver address: ");
        courier.setReceiverAddress(scanner.nextLine());
        
        System.out.print("Enter weight (kg): ");
        courier.setWeight(scanner.nextDouble());
        scanner.nextLine();
        
        System.out.print("Enter sender ID: ");
        courier.setSenderId(scanner.nextInt());
        scanner.nextLine();
        
        System.out.print("Enter receiver ID: ");
        courier.setReceiverId(scanner.nextInt());
        scanner.nextLine();
        
        System.out.print("Enter service ID: ");
        courier.setServiceId(scanner.nextInt());
        scanner.nextLine();
        
        System.out.print("Enter assigned employee ID: ");
        courier.setEmployeeId(scanner.nextInt());
        scanner.nextLine();
        
        courier.setStatus("Yet to Transit");
        courier.setDeliveryDate(new Date());
        courier.setTrackingNumber(generateTrackingNumber()); // Add tracking number generation
        
        try {
            String trackingNumber = userService.placeOrder(courier);
            System.out.println("Order placed successfully! Tracking number: " + trackingNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static String generateTrackingNumber() {
        // Simple tracking number generation (you can enhance this)
        return "TR" + System.currentTimeMillis() % 1000000;
    }
    
    private static void checkOrderStatus() {
        System.out.print("\nEnter tracking number: ");
        String trackingNumber = scanner.nextLine();
        
        try {
            String status = userService.getOrderStatus(trackingNumber);
            System.out.println("Order status: " + status);
        } catch (TrackingNumberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static void cancelOrder() {
        System.out.print("\nEnter tracking number to cancel: ");
        String trackingNumber = scanner.nextLine();
        
        try {
            boolean cancelled = userService.cancelOrder(trackingNumber);
            if (cancelled) {
                System.out.println("Order cancelled successfully!");
            } else {
                System.out.println("Failed to cancel order.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static void viewAssignedOrders() {
        System.out.print("\nEnter your employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            List<Courier> assignedOrders = userService.getAssignedOrders(employeeId);
            if (assignedOrders.isEmpty()) {
                System.out.println("No orders assigned to you.");
            } else {
                System.out.println("Your assigned orders:");
                for (Courier order : assignedOrders) {
                    System.out.println(order);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Courier Staff");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Courier Status");
            System.out.println("4. View Employee by ID");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            try {
                switch (choice) {
                    case 1:
                        addCourierStaff();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        updateCourierStatus();
                        break;
                    case 4:
                        viewEmployeeById();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void addCourierStaff() {
        System.out.println("\nAdd New Courier Staff");
        
        Employee employee = new Employee();
        System.out.print("Enter employee name: ");
        employee.setEmployeeName(scanner.nextLine());
        
        System.out.print("Enter email: ");
        employee.setEmail(scanner.nextLine());
        
        System.out.print("Enter contact number: ");
        employee.setContactNumber(scanner.nextLine());
        
        System.out.print("Enter role: ");
        employee.setRole(scanner.nextLine());
        
        System.out.print("Enter salary: ");
        employee.setSalary(scanner.nextDouble());
        scanner.nextLine();
        
        try {
            int employeeId = adminService.addCourierStaff(employee);
            System.out.println("Employee added successfully! ID: " + employeeId);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static void viewAllEmployees() {
        System.out.println("\nAll Employees");
        List<Employee> employees = adminService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }
    
    private static void updateCourierStatus() {
        System.out.print("\nEnter tracking number: ");
        String trackingNumber = scanner.nextLine();
        
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        
        try {
            boolean updated = adminService.updateCourierStatus(trackingNumber, status);
            if (updated) {
                System.out.println("Status updated successfully!");
            } else {
                System.out.println("Failed to update status.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
    
    private static void viewEmployeeById() {
        System.out.print("\nEnter employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Employee emp = adminService.getEmployeeById(employeeId);
            System.out.println("Employee details:");
            System.out.println(emp);
        } catch (InvalidEmployeeIdException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}