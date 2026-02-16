package org.example.revhire.dto.response;

public class ResumeExperienceResponse {
    private Integer id;
    private String company;
    private String role;
    private String duration;
    private String description;

    public ResumeExperienceResponse() {
    }

    public ResumeExperienceResponse(Integer id, String company, String role, String duration, String description) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.duration = duration;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
