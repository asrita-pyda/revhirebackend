package org.example.revhire.dto.response;

public class ResumeExperienceResponse {
    private Integer id;
    private String company;
    private String jobTitle;
    private String duration;
    private String description;

    public ResumeExperienceResponse() {
    }

    public ResumeExperienceResponse(Integer id, String company, String jobTitle, String duration, String description) {
        this.id = id;
        this.company = company;
        this.jobTitle = jobTitle;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
