package com.hexaware.career.presentation;

import com.hexaware.career.entity.*;
import com.hexaware.career.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    private static JobService jobService = new JobServiceImpl();
    private static ApplicantService applicantService = new ApplicantServiceImpl();
    private static JobApplicationService jobApplicationService = new JobApplicationServiceImpl();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== CareerHub Job Board ====");
            System.out.println("1. View All Job Listings");
            System.out.println("2. Create Applicant Profile");
            System.out.println("3. Apply for a Job");
            System.out.println("4. Post a New Job (Company)");
            System.out.println("5. Search Jobs by Salary Range");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewAllJobs();
                case 2 -> createApplicantProfile(sc);
                case 3 -> applyForJob(sc);
                case 4 -> postNewJob(sc);
                case 5 -> searchBySalaryRange(sc);
                case 0 -> System.out.println("Exiting CareerHub. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        sc.close();
    }

    private static void viewAllJobs() {
        List<JobListing> jobs = jobService.getJobs();
        System.out.println("\n=== Job Listings ===");
        for (JobListing job : jobs) {
            System.out.println("Job ID: " + job.getJobID());
            System.out.println("Title: " + job.getJobTitle());
            System.out.println("Company ID: " + job.getCompanyID());
            System.out.println("Location: " + job.getJobLocation());
            System.out.println("Salary: $" + job.getSalary());
            System.out.println("Posted: " + job.getPostedDate());
            System.out.println("---------------------------");
        }
    }

    private static void createApplicantProfile(Scanner sc) {
        System.out.println("\n=== Create Applicant Profile ===");
        Applicant applicant = new Applicant();

        System.out.print("First Name: ");
        applicant.setFirstName(sc.nextLine());
        System.out.print("Last Name: ");
        applicant.setLastName(sc.nextLine());
        System.out.print("Email: ");
        applicant.setEmail(sc.nextLine());
        System.out.print("Phone: ");
        applicant.setPhone(sc.nextLine());
        System.out.print("Resume Text: ");
        applicant.setResume(sc.nextLine());
        System.out.print("City: ");
        applicant.setCity(sc.nextLine());
        System.out.print("State: ");
        applicant.setState(sc.nextLine());
        System.out.print("Years of Experience: ");
        applicant.setExperienceYears(sc.nextInt());
        sc.nextLine();

        applicantService.addApplicant(applicant);
        System.out.println("Applicant profile created successfully.");
    }

    private static void applyForJob(Scanner sc) {
        System.out.println("\n=== Apply for Job ===");
        JobApplication application = new JobApplication();

        System.out.print("Applicant ID: ");
        application.setApplicantID(sc.nextInt());
        System.out.print("Job ID: ");
        application.setJobID(sc.nextInt());
        sc.nextLine();
        System.out.print("Cover Letter: ");
        application.setCoverLetter(sc.nextLine());
        application.setApplicationDate(LocalDateTime.now());

        jobApplicationService.applyForJob(application);
        System.out.println("Application submitted successfully.");
    }

    private static void postNewJob(Scanner sc) {
        System.out.println("\n=== Post a New Job ===");
        JobListing job = new JobListing();

        System.out.print("Company ID: ");
        job.setCompanyID(sc.nextInt());
        sc.nextLine();
        System.out.print("Job Title: ");
        job.setJobTitle(sc.nextLine());
        System.out.print("Job Description: ");
        job.setJobDescription(sc.nextLine());
        System.out.print("Job Location: ");
        job.setJobLocation(sc.nextLine());
        System.out.print("Salary: ");
        job.setSalary(sc.nextDouble());
        sc.nextLine();
        System.out.print("Job Type (FULL_TIME, PART_TIME, CONTRACT): ");
        job.setJobType(sc.nextLine());
        job.setPostedDate(LocalDateTime.now());

        jobService.addJob(job);
        System.out.println("Job posted successfully.");
    }

    private static void searchBySalaryRange(Scanner sc) {
        System.out.println("\n=== Search Jobs by Salary Range ===");
        System.out.print("Enter minimum salary: ");
        double min = sc.nextDouble();
        System.out.print("Enter maximum salary: ");
        double max = sc.nextDouble();

        List<JobListing> jobs = jobService.getJobsBySalaryRange(min, max);
        if (jobs.isEmpty()) {
            System.out.println("No jobs found in the given salary range.");
        } else {
            for (JobListing job : jobs) {
                System.out.println("Job Title: " + job.getJobTitle());
                System.out.println("Company ID: " + job.getCompanyID());
                System.out.println("Salary: $" + job.getSalary());
                System.out.println("---------------------------");
            }
        }
    }
}
