package com.hexaware.career.entity;

import java.time.LocalDateTime;

public class JobApplication {
	public JobApplication() {
		super();
	}
	public JobApplication(int applicationID, int jobID, int applicantID, LocalDateTime applicationDate,
			String coverLetter) {
		super();
		this.applicationID = applicationID;
		this.jobID = jobID;
		this.applicantID = applicantID;
		this.applicationDate = applicationDate;
		this.coverLetter = coverLetter;
	}
	private int applicationID;
    private int jobID;
    private int applicantID;
    private LocalDateTime applicationDate;
    private String coverLetter;
	public int getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public int getApplicantID() {
		return applicantID;
	}
	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}
	public LocalDateTime getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getCoverLetter() {
		return coverLetter;
	}
	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}
	@Override
	public String toString() {
		return "JobApplication [applicationID=" + applicationID + ", jobID=" + jobID + ", applicantID=" + applicantID
				+ ", applicationDate=" + applicationDate + ", coverLetter=" + coverLetter + "]";
	}
}
