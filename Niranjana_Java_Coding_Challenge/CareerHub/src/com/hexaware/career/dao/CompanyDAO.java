package com.hexaware.career.dao;

import com.hexaware.career.entity.Company;
import java.util.List;

public interface CompanyDAO {
    void insertCompany(Company company);
    List<Company> getAllCompanies();
}