package com.hexaware.career.entity;

public class Applicant {
	
	public Applicant() {
		super();
	}
	public Applicant(int applicantID, String firstName, String lastName, String email, String phone, String resume,
			String city, String state, int experienceYears) {
		super();
		this.applicantID = applicantID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.resume = resume;
		this.city = city;
		this.state = state;
		this.experienceYears = experienceYears;
	}
	private int applicantID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String resume;
    private String city;
    private String state;
    private int experienceYears;
	public int getApplicantID() {
		return applicantID;
	}
	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getExperienceYears() {
		return experienceYears;
	}
	public void setExperienceYears(int experienceYears) {
		this.experienceYears = experienceYears;
	}
	@Override
	public String toString() {
		return "Applicant [applicantID=" + applicantID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phone=" + phone + ", resume=" + resume + ", city=" + city + ", state=" + state
				+ ", experienceYears=" + experienceYears + "]";
	}
    
	
}
