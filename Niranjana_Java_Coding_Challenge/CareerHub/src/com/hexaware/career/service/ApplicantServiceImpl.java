package com.hexaware.career.service;

import com.hexaware.career.dao.ApplicantDAO;
import com.hexaware.career.dao.ApplicantDAOImpl;
import com.hexaware.career.entity.Applicant;
import java.util.List;

public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantDAO dao = new ApplicantDAOImpl();

    @Override
    public void addApplicant(Applicant applicant) {
        dao.insertApplicant(applicant);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return dao.getAllApplicants();
    }
}
