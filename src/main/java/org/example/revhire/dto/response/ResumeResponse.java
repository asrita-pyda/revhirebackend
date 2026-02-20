package org.example.revhire.dto.response;

import java.util.List;
import java.util.Objects;

public class ResumeResponse {
    private ResumeObjectiveResponse objective;
    private List<ResumeEducationResponse> education;
    private List<ResumeExperienceResponse> experience;
    private List<ResumeSkillsResponse> skills;
    private List<ResumeProjectsResponse> projects;
    private List<ResumeCertificationsResponse> certifications;
    private List<ResumeFilesResponse> files;

    public ResumeResponse() {
    }

    public ResumeResponse(ResumeObjectiveResponse objective, List<ResumeEducationResponse> education,
                          List<ResumeExperienceResponse> experience,
                          List<ResumeSkillsResponse> skills, List<ResumeProjectsResponse> projects,
                          List<ResumeCertificationsResponse> certifications,
                          List<ResumeFilesResponse> files) {
        this.objective = objective;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
        this.projects = projects;
        this.certifications = certifications;
        this.files = files;
    }

    public ResumeObjectiveResponse getObjective() {
        return objective;
    }

    public void setObjective(ResumeObjectiveResponse objective) {
        this.objective = objective;
    }

    public List<ResumeEducationResponse> getEducation() {
        return education;
    }

    public void setEducation(List<ResumeEducationResponse> education) {
        this.education = education;
    }

    public List<ResumeExperienceResponse> getExperience() {
        return experience;
    }

    public void setExperience(List<ResumeExperienceResponse> experience) {
        this.experience = experience;
    }

    public List<ResumeSkillsResponse> getSkills() {
        return skills;
    }

    public void setSkills(List<ResumeSkillsResponse> skills) {
        this.skills = skills;
    }

    public List<ResumeProjectsResponse> getProjects() {
        return projects;
    }

    public void setProjects(List<ResumeProjectsResponse> projects) {
        this.projects = projects;
    }

    public List<ResumeCertificationsResponse> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<ResumeCertificationsResponse> certifications) {
        this.certifications = certifications;
    }

    public List<ResumeFilesResponse> getFiles() {
        return files;
    }

    public void setFiles(List<ResumeFilesResponse> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ResumeResponse that = (ResumeResponse) o;
        return Objects.equals(objective, that.objective);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objective);
    }

    @Override
    public String toString() {
        return "ResumeResponse{" +
                "objective=" + objective +
                ", education=" + education +
                ", experience=" + experience +
                ", skills=" + skills +
                ", projects=" + projects +
                ", certifications=" + certifications +
                ", files=" + files +
                '}';
    }
}
