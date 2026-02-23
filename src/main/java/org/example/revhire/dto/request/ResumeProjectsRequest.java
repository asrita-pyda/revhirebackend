package org.example.revhire.dto.request;

public class ResumeProjectsRequest {
    private String title;
    private String description;
    private String role;
    private String techStack;

    public ResumeProjectsRequest() {
    }

    public ResumeProjectsRequest(String title, String description, String role, String techStack) {
        this.title = title;
        this.description = description;
        this.role = role;
        this.techStack = techStack;
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

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }
}
