package org.example.revhire.dto.response;

public class ResumeProjectsResponse {
    private Integer id;
    private String title;
    private String description;
    private String role;
    private String techStack;

    public ResumeProjectsResponse() {
    }

    public ResumeProjectsResponse(Integer id, String title, String description, String role, String techStack) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.role = role;
        this.techStack = techStack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

