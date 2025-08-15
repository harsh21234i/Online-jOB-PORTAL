package com.jobportal.job_portal.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name="seeker_id")
    private User seeker;

    @Enumerated(EnumType.STRING)
    private Status status = Status.APPLIED;

    private LocalDateTime appliedAt = LocalDateTime.now();

    public enum Status { APPLIED, SHORTLISTED, REJECTED }

    // Getters & Setters
}
