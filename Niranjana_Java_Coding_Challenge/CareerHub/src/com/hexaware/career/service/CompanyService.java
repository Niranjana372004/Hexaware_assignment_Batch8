package com.hexaware.career.service;

import com.hexaware.career.entity.Company;
import java.util.List;

public interface CompanyService {
    void addCompany(Company company);
    List<Company> getAllCompanies();
}
