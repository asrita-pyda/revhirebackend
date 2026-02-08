package org.example.revhire.model;
import jakarta.persistence.*;
import org.example.revhire.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "resume_files")
public class ResumeFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "file_path", length = 255)
    private String filePath;

    @Column(name = "file_type", length = 20)
    private String fileType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    public ResumeFiles() {
    }

    public ResumeFiles(Long id, User user, String fileName, String filePath, String fileType, LocalDateTime uploadedAt) {
        this.id = id;
        this.user = user;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}

