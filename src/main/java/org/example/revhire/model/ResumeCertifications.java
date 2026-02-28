package org.example.revhire.model;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "resume_certifications")
public class ResumeCertifications extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String name;
    private String issuingOrganization;
    private java.time.LocalDate issueDate;
    private java.time.LocalDate expiryDate;
    private String credentialUrl;

    public ResumeCertifications() {
    }

    public ResumeCertifications(Integer id, User user, String name, String issuingOrganization,
                                java.time.LocalDate issueDate, java.time.LocalDate expiryDate, String credentialUrl) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.credentialUrl = credentialUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public java.time.LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(java.time.LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public java.time.LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(java.time.LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }
}
