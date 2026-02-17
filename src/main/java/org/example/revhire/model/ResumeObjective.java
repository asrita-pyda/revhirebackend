package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resume_objective")
public class ResumeObjective extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String objective;

    public ResumeObjective() {
    }

    public ResumeObjective(Integer id, User user, String objective) {
        this.id = id;
        this.user = user;
        this.objective = objective;
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

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
}
