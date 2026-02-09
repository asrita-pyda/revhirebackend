package org.example.revhire.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(
        name = "saved_jobs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "job_id"})
        }
)
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "job_id", nullable = false)
    private int jobId;

    @Column(name = "saved_at")
    private Timestamp savedAt;


    public SavedJob() {
    }

    public SavedJob(int id, int userId, int jobId, Timestamp savedAt) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.savedAt = savedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
}

