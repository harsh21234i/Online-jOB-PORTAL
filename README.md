🏢 Online Job Portal

An Online Job Portal web application that connects job seekers with employers.
Job seekers can search and apply for jobs, while employers can post and manage vacancies.

🚀 Features
For Job Seekers:

Create and manage profiles

Search and filter jobs by category, location, and keywords

Apply for jobs directly through the portal

Track application status

For Employers:

Post new job listings

Manage and edit job postings

View applicant details

Shortlist or reject candidates

🛠️ Tech Stack

Frontend: HTML, CSS, JavaScript (or React if used)

Backend: Java / Spring Boot

Database: MySQL

Build Tool: Maven

Version Control: Git & GitHub

## **STRUCTURE**

job-portal/
├── src/
│ ├── main/
│ │ └── java/com/jobportal/job_portal/
│ │ ├── JobPortalApplication.java # Main Spring Boot class
│ │ ├── entity/ # JPA Entities
│ │ │ ├── User.java
│ │ │ └── Job.java
│ │ ├── repository/ # Spring Data JPA Repositories
│ │ │ ├── UserRepository.java
│ │ │ ├── JobRepository.java
│ │ │ └── ApplicationRepository.java
│ │ ├── service/ # Business Logic
│ │ │ ├── UserService.java
│ │ │ ├── JobService.java
│ │ │ └── ApplicationService.java
│ │ └── controller/ # REST APIs
│ │ ├── UserController.java
│ │ ├── JobController.java
│ │ └── ApplicationController.java
│ └── resources/
│ ├── application.properties # DB configs
│ └── application-test.properties
└── test/java/com/jobportal/job_portal/ # Unit tests
└── JobPortalApplicationTests.java
pom.xml # Maven dependencies
README.md
