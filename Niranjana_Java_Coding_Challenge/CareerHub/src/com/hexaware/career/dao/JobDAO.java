package com.hexaware.career.dao;

import com.hexaware.career.entity.JobListing;
import java.util.List;

public interface JobDAO {
    void insertJob(JobListing job);
    List<JobListing> getAllJobs();
    List<JobListing> getAllJobListingsWithCompany();
    List<JobListing> getJobsBySalaryRange(double minSalary, double maxSalary);


}