package com.hexaware.courier.presentation;

import com.hexaware.courier.dao.*;
import com.hexaware.courier.entity.*;
import com.hexaware.courier.exception.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    private static Scanner scanner = new Scanner(System.in);
    private static ICourierUserService userService;
    private static ICourierAdminService adminService;
    
    public static void main(String[] args) {
        try {
            // Initialize separate services
            userService = new CourierUserServiceImpl();
            adminService = new CourierAdminServiceImpl();
            
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
    
    private static void placeOrder() throws SQLException {
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
        
        String trackingNumber = userService.placeOrder(courier);
        if (trackingNumber != null) {
            System.out.println("Order placed successfully! Tracking number: " + trackingNumber);
        } else {
            System.out.println("Failed to place order.");
        }
    }
    
    private static void checkOrderStatus() throws SQLException, TrackingNumberNotFoundException {
        System.out.print("\nEnter tracking number: ");
        String trackingNumber = scanner.nextLine();
        
        try {
            String status = userService.getOrderStatus(trackingNumber);
            System.out.println("Order status: " + status);
        } catch (TrackingNumberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void cancelOrder() throws SQLException {
        System.out.print("\nEnter tracking number to cancel: ");
        String trackingNumber = scanner.nextLine();
        
        boolean cancelled = userService.cancelOrder(trackingNumber);
        if (cancelled) {
            System.out.println("Order cancelled successfully!");
        } else {
            System.out.println("Failed to cancel order. Tracking number not found.");
        }
    }
    
    private static void viewAssignedOrders() throws SQLException {
        System.out.print("\nEnter your employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        
        List<Courier> assignedOrders = userService.getAssignedOrder(employeeId);
        if (assignedOrders.isEmpty()) {
            System.out.println("No orders assigned to you.");
        } else {
            System.out.println("Your assigned orders:");
            for (Courier order : assignedOrders) {
                System.out.println(order);
            }
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
    
    private static void addCourierStaff() throws SQLException {
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
        
        int employeeId = adminService.addCourierStaff(employee);
        if (employeeId > 0) {
            System.out.println("Employee added successfully! ID: " + employeeId);
        } else {
            System.out.println("Failed to add employee.");
        }
    }
    
    private static void viewAllEmployees() throws SQLException {
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
    
    private static void updateCourierStatus() throws SQLException {
        System.out.print("\nEnter tracking number: ");
        String trackingNumber = scanner.nextLine();
        
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        
        boolean updated = adminService.updateCourierStatus(trackingNumber, status);
        if (updated) {
            System.out.println("Status updated successfully!");
        } else {
            System.out.println("Failed to update status. Tracking number not found.");
        }
    }
    
    private static void viewEmployeeById() throws SQLException {
        System.out.print("\nEnter employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Employee emp = adminService.getEmployeeById(employeeId);
            System.out.println("Employee details:");
            System.out.println(emp);
        } catch (InvalidEmployeeIdException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}