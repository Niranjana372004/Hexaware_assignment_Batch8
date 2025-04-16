package com.hexaware.career.service;

import com.hexaware.career.entity.JobApplication;
import java.util.List;

public interface JobApplicationService {
    void applyForJob(JobApplication application);
    List<JobApplication> getApplicationsForJob(int jobId);
}
