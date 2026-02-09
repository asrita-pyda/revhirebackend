package org.example.revhire.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_views")
public class JobView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime viewedAt = LocalDateTime.now();

    public JobView(){

    }

    public JobView(Long id, Job job, User user, LocalDateTime viewedAt) {
        this.id = id;
        this.job = job;
        this.user = user;
        this.viewedAt = viewedAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }
}
