package com.hexaware.career.dao;

import com.hexaware.career.entity.Applicant;
import java.util.List;

public interface ApplicantDAO {
    void insertApplicant(Applicant applicant);
    List<Applicant> getAllApplicants();

}