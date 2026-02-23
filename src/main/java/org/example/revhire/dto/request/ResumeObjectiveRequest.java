package org.example.revhire.dto.request;

public class ResumeObjectiveRequest {
    private String objective;

    public ResumeObjectiveRequest() {
    }

    public ResumeObjectiveRequest(String objective) {
        this.objective = objective;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
}
