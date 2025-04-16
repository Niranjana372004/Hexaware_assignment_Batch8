package com.hexaware.career.dao;

import com.hexaware.career.entity.JobApplication;
import java.util.List;

public interface JobApplicationDAO {
    void insertJobApplication(JobApplication application);
    List<JobApplication> getApplicationsForJob(int jobId);
    void insertApplication(JobApplication application);

}
