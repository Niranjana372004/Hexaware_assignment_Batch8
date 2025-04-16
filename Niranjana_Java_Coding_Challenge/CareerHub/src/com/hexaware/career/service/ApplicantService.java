package com.hexaware.career.service;

import com.hexaware.career.entity.Applicant;
import java.util.List;

public interface ApplicantService {
    void addApplicant(Applicant applicant);
    List<Applicant> getAllApplicants();
}
