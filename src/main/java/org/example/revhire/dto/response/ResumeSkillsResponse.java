package org.example.revhire.dto.response;

public class ResumeSkillsResponse {
    private Integer id;
    private String skillName;
    private String proficiency;

    public ResumeSkillsResponse() {
    }

    public ResumeSkillsResponse(Integer id, String skillName, String proficiency) {
        this.id = id;
        this.skillName = skillName;
        this.proficiency = proficiency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}

