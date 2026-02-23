package org.example.revhire.service;

import org.example.revhire.dto.request.*;
import org.example.revhire.dto.response.ResumeResponse;
import org.example.revhire.model.*;

public interface ResumeService {
    ResumeResponse getResumeByUserId(Long userId);

    ResumeObjective saveObjective(Long userId, ResumeObjectiveRequest objective);

    ResumeEducation addEducation(Long userId, ResumeEducationRequest education);

    ResumeEducation updateEducation(Integer id, ResumeEducationRequest education);

    void deleteEducation(Integer id);

    ResumeExperience addExperience(Long userId, ResumeExperienceRequest experience);

    ResumeExperience updateExperience(Integer id, ResumeExperienceRequest experience);

    void deleteExperience(Integer id);

    ResumeSkills addSkill(Long userId, ResumeSkillsRequest skill);

    ResumeSkills updateSkill(Integer id, ResumeSkillsRequest skill);

    void deleteSkill(Integer id);

    ResumeProjects addProject(Long userId, ResumeProjectsRequest project);

    ResumeProjects updateProject(Integer id, ResumeProjectsRequest project);

    void deleteProject(Integer id);

    ResumeCertifications addCertification(Long userId, ResumeCertificationsRequest certification);

    ResumeCertifications updateCertification(Integer id, ResumeCertificationsRequest certification);

    void deleteCertification(Integer id);

    ResumeFiles uploadResumeFile(Long userId, org.springframework.web.multipart.MultipartFile file);

    ResumeFiles getResumeFile(Long fileId);

    void deleteResumeFile(Long fileId);

    java.util.List<ResumeSkills> getSkills(Long userId);

    java.util.List<ResumeEducation> getEducation(Long userId);

    java.util.List<ResumeExperience> getExperience(Long userId);

    java.util.List<ResumeProjects> getProjects(Long userId);

    java.util.List<ResumeCertifications> getCertifications(Long userId);

    java.util.List<ResumeResponse> searchBySkill(String skillName);

    java.util.List<ResumeResponse> searchByLocation(String location);

    java.util.List<String> getAllSeekerSkills();

    long getTotalResumeCount();

    void clearResume(Long userId);
}
