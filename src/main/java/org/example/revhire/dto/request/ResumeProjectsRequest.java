package org.example.revhire.dto.request;

public class ResumeProjectsRequest {
    private String title;
    private String description;
    private String role;
    private String technologies;
    private String link;

    public ResumeProjectsRequest() {
    }

    public ResumeProjectsRequest(String title, String description, String role, String technologies, String link) {
        this.title = title;
        this.description = description;
        this.role = role;
        this.technologies = technologies;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
