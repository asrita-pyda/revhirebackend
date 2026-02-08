package org.example.revhire.model;
import jakarta.persistence.*;
import org.example.revhire.User;
@Entity
@Table(name = "resume_objective")
public class ResumeObjective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String objective;

    public ResumeObjective() {
    }

    public ResumeObjective(Long id, User user, String objective) {
        this.id = id;
        this.user = user;
        this.objective = objective;
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

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
}

