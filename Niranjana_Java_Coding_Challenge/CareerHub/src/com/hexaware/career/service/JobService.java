package com.hexaware.career.service;

import com.hexaware.career.entity.JobListing;
import java.util.List;

public interface JobService {
    void addJob(JobListing job);
    List<JobListing> getJobs();
    List<JobListing> getJobsBySalaryRange(double minSalary, double maxSalary);

}