package com.hexaware.career.service;

import com.hexaware.career.dao.JobApplicationDAO;
import com.hexaware.career.dao.JobApplicationDAOImpl;
import com.hexaware.career.entity.JobApplication;
import java.util.List;

public class JobApplicationServiceImpl implements JobApplicationService {

    private JobApplicationDAO dao = new JobApplicationDAOImpl();

    @Override
    public void applyForJob(JobApplication application) {
        dao.insertJobApplication(application);
    }

    @Override
    public List<JobApplication> getApplicationsForJob(int jobId) {
        return dao.getApplicationsForJob(jobId);
    }
}
