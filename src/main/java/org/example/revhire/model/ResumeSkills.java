package org.example.revhire.model;
import jakarta.persistence.*;

@Entity
@Table(name = "resume_skills")
public class ResumeSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "skill_name", length = 100)
    private String skillName;

    public ResumeSkills() {
    }

    public ResumeSkills(Long id, User user, String skillName) {
        this.id = id;
        this.user = user;
        this.skillName = skillName;
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

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
