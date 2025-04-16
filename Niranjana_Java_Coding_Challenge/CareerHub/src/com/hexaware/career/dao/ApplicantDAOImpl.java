package com.hexaware.career.dao;

import com.hexaware.career.entity.Applicant;
import com.hexaware.career.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDAOImpl implements ApplicantDAO {
    private Connection conn = DBConnUtil.getConnection("src/db.properties");

    public void insertApplicant(Applicant applicant) {
        String sql = "INSERT INTO applicants (FirstName, LastName, Email, Phone, Resume, Applicants_City, Applicants_State, ExperienceYears) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, applicant.getFirstName());
            pstmt.setString(2, applicant.getLastName());
            pstmt.setString(3, applicant.getEmail());
            pstmt.setString(4, applicant.getPhone());
            pstmt.setString(5, applicant.getResume());
            pstmt.setString(6, applicant.getCity());
            pstmt.setString(7, applicant.getState());
            pstmt.setInt(8, applicant.getExperienceYears());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Applicant> getAllApplicants() {
        List<Applicant> list = new ArrayList<>();
        String sql = "SELECT * FROM applicants";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Applicant a = new Applicant(
                    rs.getInt("ApplicantID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Resume"),
                    rs.getString("Applicants_City"),
                    rs.getString("Applicants_State"),
                    rs.getInt("ExperienceYears")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}