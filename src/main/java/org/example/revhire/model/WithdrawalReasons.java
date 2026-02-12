package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "withdrawal_reasons")
public class WithdrawalReasons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Applications application;

    @Column(columnDefinition = "TEXT")
    private String reason;


    public WithdrawalReasons() {

    }


    public WithdrawalReasons(Applications application, String reason) {
        this.application = application;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Applications getApplication() {
        return application;
    }

    public void setApplication(Applications application) {
        this.application = application;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
