package com.hexaware.courier.dao;

import java.util.List;
import com.hexaware.courier.entity.Courier;
import com.hexaware.courier.entity.Employee;
import com.hexaware.courier.exception.InvalidEmployeeIdException;

public class CourierAdminServiceCollectionImpl extends CourierUserServiceCollectionImpl implements ICourierAdminService {

    @Override
    public int addCourierStaff(Employee obj) {
        int newId = companyObj.getEmployeeDetails().size() + 1;
        obj.setEmployeeID(newId);
        companyObj.getEmployeeDetails().add(obj);
        return newId;
    }

    @Override
    public boolean updateCourierStatus(String trackingNumber, String newStatus) {
        for (Courier courier : companyObj.getCourierDetails()) {
            if (courier.getTrackingNumber().equals(trackingNumber)) {
                courier.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return companyObj.getEmployeeDetails();
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws InvalidEmployeeIdException {
        for (Employee emp : companyObj.getEmployeeDetails()) {
            if (emp.getEmployeeID() == employeeId) {
                return emp;
            }
        }
        throw new InvalidEmployeeIdException();
    }
}