package com.hexaware.career.service;

import com.hexaware.career.dao.JobDAO;
import com.hexaware.career.dao.JobDAOImpl;
import com.hexaware.career.entity.JobListing;
import java.util.List;

public class JobServiceImpl implements JobService {
    private JobDAO jobDao = new JobDAOImpl();

    public void addJob(JobListing job) {
        jobDao.insertJob(job);
    }

    public List<JobListing> getJobs() {
        return jobDao.getAllJobs();
    }
    
    @Override
    public List<JobListing> getJobsBySalaryRange(double minSalary, double maxSalary) {
        return jobDao.getJobsBySalaryRange(minSalary, maxSalary);
    }

}