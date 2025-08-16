package com.jobportal.job_portal.Entity;

import jakarta.persistence.*;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String companyName;
    @Lob   // Large Object
    @Column(columnDefinition = "TEXT")
    private String description;
    

    @ManyToOne
    private User postedBy; // The user who posted the job

    // No-arg constructor required by JPA
    public Job() {}

    // All-args constructor
    public Job(String title, String companyName, String description) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
}
