package org.example.revhire.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "resume_files")
public class ResumeFiles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String fileName;
    private String filePath;
    private String fileType;

    @Lob
    @Column(name = "file_data", length = 2097152)
    private byte[] fileData;

    public ResumeFiles() {
    }

    public ResumeFiles(Long id, User user, String fileName, String filePath, String fileType,
                       byte[] fileData) {
        this.id = id;
        this.user = user;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileData = fileData;
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public java.time.LocalDateTime getUploadedAt() {
        return getCreatedAt();
    }
}
