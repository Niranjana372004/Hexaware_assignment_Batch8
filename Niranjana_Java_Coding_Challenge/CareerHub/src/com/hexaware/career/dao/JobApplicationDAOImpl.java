package com.hexaware.career.dao;

import com.hexaware.career.entity.JobApplication;
import com.hexaware.career.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDAOImpl implements JobApplicationDAO {
    private Connection conn = DBConnUtil.getConnection("src/db.properties");

    public void insertJobApplication(JobApplication application) {
        String sql = "INSERT INTO applications (JobID, ApplicantID, ApplicationDate, CoverLetter) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection("src/db.properties"); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, application.getJobID());        
            pstmt.setInt(2, application.getApplicantID());  
            pstmt.setTimestamp(3, Timestamp.valueOf(application.getApplicationDate())); 
            pstmt.setString(4, application.getCoverLetter());
            pstmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<JobApplication> getApplicationsForJob(int jobId) {
        List<JobApplication> list = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE JobID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, jobId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JobApplication app = new JobApplication(
                    rs.getInt("ApplicationID"),
                    rs.getInt("JobID"),
                    rs.getInt("ApplicantID"),
                    rs.getTimestamp("ApplicationDate").toLocalDateTime(),
                    rs.getString("CoverLetter")
                );
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public void insertApplication(JobApplication application) {
        String sql = "INSERT INTO applications (JobID, ApplicantID, ApplicationDate, CoverLetter) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection("db.properties");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, application.getJobID());
            ps.setInt(2, application.getApplicantID());
            ps.setTimestamp(3, Timestamp.valueOf(application.getApplicationDate()));
            ps.setString(4, application.getCoverLetter());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
