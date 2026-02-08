package org.example.revhire.model;
import jakarta.persistence.*;
import org.example.revhire.User;
@Entity
@Table(name = "resume_certifications")
public class ResumeCertifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "certificate_name", length = 150)
    private String certificateName;

    @Column(length = 150)
    private String organization;

    private Integer year;

    public ResumeCertifications() {
    }

    public ResumeCertifications(Long id, User user, String certificateName, String organization, Integer year) {
        this.id = id;
        this.user = user;
        this.certificateName = certificateName;
        this.organization = organization;
        this.year = year;
    }

    public Long getId(){

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

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
