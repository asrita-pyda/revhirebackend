package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "job_seekers")
public class JobSeeker {

    @Id
    private Integer userId;

    private String currentStatus;
    private Integer totalExperience;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public JobSeeker(){

    }

    public JobSeeker(Integer userId, String currentStatus, Integer totalExperience, User user) {
        this.userId = userId;
        this.currentStatus = currentStatus;
        this.totalExperience = totalExperience;
        this.user = user;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public Integer getTotalExperience() { return totalExperience; }
    public void setTotalExperience(Integer totalExperience) { this.totalExperience = totalExperience; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
