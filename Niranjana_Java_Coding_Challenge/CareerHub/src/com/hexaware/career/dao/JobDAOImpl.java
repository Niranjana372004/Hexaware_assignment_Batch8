package com.hexaware.career.dao;

import com.hexaware.career.entity.JobListing;
import com.hexaware.career.util.DBConnUtil;
import java.sql.*;
import java.util.*;

public class JobDAOImpl implements JobDAO {
    private final String fileName = "src/db.properties";

    public void insertJob(JobListing job) {
        String sql = "INSERT INTO jobs (CompanyID, JobTitle, JobDescription, JobLocation, Salary, JobType, PostedDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection(fileName);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, job.getCompanyID());
            ps.setString(2, job.getJobTitle());
            ps.setString(3, job.getJobDescription());
            ps.setString(4, job.getJobLocation());
            ps.setDouble(5, job.getSalary());
            ps.setString(6, job.getJobType());
            ps.setTimestamp(7, Timestamp.valueOf(job.getPostedDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JobListing> getAllJobs() {
        List<JobListing> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs";
        try (Connection conn = DBConnUtil.getConnection(fileName);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JobListing job = new JobListing(
                    rs.getInt("JobID"),
                    rs.getInt("CompanyID"),
                    rs.getString("JobTitle"),
                    rs.getString("JobDescription"),
                    rs.getString("JobLocation"),
                    rs.getDouble("Salary"),
                    rs.getString("JobType"),
                    rs.getTimestamp("PostedDate").toLocalDateTime()
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    @Override
    public List<JobListing> getAllJobListingsWithCompany() {
        List<JobListing> jobs = new ArrayList<>();
        String sql = "SELECT j.JobID, j.CompanyID, j.JobTitle, j.Salary, j.JobDescription, j.JobLocation, j.JobType, j.PostedDate, c.CompanyName " +
                     "FROM jobs j JOIN companies c ON j.CompanyID = c.CompanyID";

        try (Connection conn = DBConnUtil.getConnection(fileName);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                JobListing job = new JobListing(
                    rs.getInt("JobID"),
                    rs.getInt("CompanyID"),
                    rs.getString("JobTitle"),
                    rs.getString("JobDescription"),
                    rs.getString("JobLocation"),
                    rs.getDouble("Salary"),
                    rs.getString("JobType"),
                    rs.getTimestamp("PostedDate").toLocalDateTime()
                );
                job.setCompanyName(rs.getString("CompanyName"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
    @Override
    public List<JobListing> getJobsBySalaryRange(double minSalary, double maxSalary) {
        List<JobListing> jobs = new ArrayList<>();
        String sql = "SELECT j.JobID, j.CompanyID, j.JobTitle, j.Salary, j.JobDescription, j.JobLocation, j.JobType, j.PostedDate, c.CompanyName " +
                     "FROM jobs j JOIN companies c ON j.CompanyID = c.CompanyID " +
                     "WHERE j.Salary BETWEEN ? AND ?";
        try (Connection conn = DBConnUtil.getConnection(fileName);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, minSalary);
            ps.setDouble(2, maxSalary);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JobListing job = new JobListing(
                        rs.getInt("JobID"),
                        rs.getInt("CompanyID"),
                        rs.getString("JobTitle"),
                        rs.getString("JobDescription"),
                        rs.getString("JobLocation"),
                        rs.getDouble("Salary"),
                        rs.getString("JobType"),
                        rs.getTimestamp("PostedDate").toLocalDateTime()
                    );
                    job.setCompanyName(rs.getString("CompanyName")); 
                    jobs.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

}
