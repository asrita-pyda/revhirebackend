package org.example.revhire.model;
import jakarta.persistence.*;
import org.example.revhire.model.User;
@Entity
@Table(name = "resume_projects")
public class ResumeProjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String role;

    @Column(name = "tech_stack", length = 200)
    private String techStack;

    public ResumeProjects() {
    }

    public ResumeProjects(Long id, User user, String title, String description, String role, String techStack) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.role = role;
        this.techStack = techStack;
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

