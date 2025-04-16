package com.hexaware.career.service;

import com.hexaware.career.dao.CompanyDAO;
import com.hexaware.career.dao.CompanyDAOImpl;
import com.hexaware.career.entity.Company;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDAO dao = new CompanyDAOImpl();

    @Override
    public void addCompany(Company company) {
        dao.insertCompany(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return dao.getAllCompanies();
    }
}
