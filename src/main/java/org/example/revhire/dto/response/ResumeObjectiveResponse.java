package org.example.revhire.dto.response;

public class ResumeObjectiveResponse {
    private Integer id;
    private String objective;

    public ResumeObjectiveResponse() {
    }

    public ResumeObjectiveResponse(Integer id, String objective) {
        this.id = id;
        this.objective = objective;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
}
