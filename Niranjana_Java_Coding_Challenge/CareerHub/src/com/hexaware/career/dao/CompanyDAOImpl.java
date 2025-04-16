package com.hexaware.career.dao;

import com.hexaware.career.entity.Company;
import com.hexaware.career.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    // Insert a new company into the database
    @Override
    public void insertCompany(Company company) {
        String sql = "INSERT INTO companies (CompanyName, Location, City, State) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection("db.properties");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getCompanyName());
            pstmt.setString(2, company.getLocation());
            pstmt.setString(3, company.getCity());
            pstmt.setString(4, company.getState());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all companies from the database
    @Override
    public List<Company> getAllCompanies() {
        List<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM companies";
        try (Connection conn = DBConnUtil.getConnection("src/db.properties");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Company c = new Company(
                    rs.getInt("CompanyID"),
                    rs.getString("CompanyName"),
                    rs.getString("Location"),
                    rs.getString("City"),
                    rs.getString("State")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
