package org.example.revhire.service;

import org.example.revhire.dto.request.*;
import org.example.revhire.dto.response.ResumeResponse;
import org.example.revhire.mapper.ResumeMapper;
import org.example.revhire.model.*;
import org.example.revhire.repository.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeObjectiveRepository objectiveRepo;
    private final ResumeEducationRepository educationRepo;
    private final ResumeExperienceRepository experienceRepo;
    private final ResumeSkillRepository skillRepo;
    private final ResumeProjectRepository projectRepo;
    private final ResumeCertificationRepository certificationRepo;
    private final ResumeFileRepository fileRepo;
    private final UserRepository userRepository;
    private final org.example.revhire.mapper.ResumeMapper resumeMapper;

    public ResumeServiceImpl(ResumeObjectiveRepository objectiveRepo, ResumeEducationRepository educationRepo,
                             ResumeExperienceRepository experienceRepo, ResumeSkillRepository skillRepo,
                             ResumeProjectRepository projectRepo, ResumeCertificationRepository certificationRepo,
                             ResumeFileRepository fileRepo, UserRepository userRepository,
                             ResumeMapper resumeMapper) {
        this.objectiveRepo = objectiveRepo;
        this.educationRepo = educationRepo;
        this.experienceRepo = experienceRepo;
        this.skillRepo = skillRepo;
        this.projectRepo = projectRepo;
        this.certificationRepo = certificationRepo;
        this.fileRepo = fileRepo;
        this.userRepository = userRepository;
        this.resumeMapper = resumeMapper;
    }

    @Override
    public ResumeResponse getResumeByUserId(Long userId) {
        ResumeResponse dto = new ResumeResponse();
        dto.setObjective(resumeMapper.toDto(objectiveRepo.findByUserId(userId).orElse(null)));
        dto.setEducation(resumeMapper.toEducationDtoList(educationRepo.findByUserId(userId)));
        dto.setExperience(resumeMapper.toExperienceDtoList(experienceRepo.findByUserId(userId)));
        dto.setSkills(resumeMapper.toSkillsDtoList(skillRepo.findByUserId(userId)));
        dto.setProjects(resumeMapper.toProjectsDtoList(projectRepo.findByUserId(userId)));
        dto.setCertifications(resumeMapper.toCertificationsDtoList(certificationRepo.findByUserId(userId)));
        dto.setFiles(resumeMapper.toFilesDtoList(fileRepo.findByUserId(userId)));
        return dto;
    }

    @Override
    @Transactional
    public ResumeObjective saveObjective(Long userId, ResumeObjectiveRequest request) {
        User user = getUser(userId);
        ResumeObjective existing = objectiveRepo.findByUserId(userId).orElse(new ResumeObjective());
        existing.setUser(user);
        existing.setObjective(request.getObjective());
        return objectiveRepo.save(existing);
    }

    @Override
    public ResumeEducation addEducation(Long userId, ResumeEducationRequest request) {
        ResumeEducation education = resumeMapper.toEntity(request);
        education.setUser(getUser(userId));
        return educationRepo.save(education);
    }

    @Override
    public ResumeEducation updateEducation(Integer id, ResumeEducationRequest request) {
        ResumeEducation education = educationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found"));
        education.setDegree(request.getDegree());
        education.setInstitution(request.getInstitution());
        education.setYear(request.getYear());
        education.setCgpa(request.getCgpa());
        return educationRepo.save(education);
    }

    @Override
    public void deleteEducation(Integer id) {
        educationRepo.deleteById(id);
    }

    @Override
    public ResumeExperience addExperience(Long userId, ResumeExperienceRequest request) {
        ResumeExperience experience = resumeMapper.toEntity(request);
        experience.setUser(getUser(userId));
        return experienceRepo.save(experience);
    }

    @Override
    public ResumeExperience updateExperience(Integer id, ResumeExperienceRequest request) {
        ResumeExperience experience = experienceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
        experience.setJobTitle(request.getJobTitle());
        experience.setCompany(request.getCompany());
        experience.setDuration(request.getDuration());
        experience.setDescription(request.getDescription());
        return experienceRepo.save(experience);
    }

    @Override
    public void deleteExperience(Integer id) {
        experienceRepo.deleteById(id);
    }

    @Override
    public ResumeSkills addSkill(Long userId, ResumeSkillsRequest request) {
        ResumeSkills skills = resumeMapper.toEntity(request);
        skills.setUser(getUser(userId));
        return skillRepo.save(skills);
    }

    @Override
    public ResumeSkills updateSkill(Integer id, ResumeSkillsRequest request) {
        ResumeSkills skill = skillRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setSkillName(request.getSkillName());
        skill.setProficiency(request.getProficiency());
        return skillRepo.save(skill);
    }

    @Override
    public void deleteSkill(Integer id) {
        skillRepo.deleteById(id);
    }

    @Override
    public ResumeProjects addProject(Long userId, ResumeProjectsRequest request) {
        ResumeProjects projects = resumeMapper.toEntity(request);
        projects.setUser(getUser(userId));
        return projectRepo.save(projects);
    }

    @Override
    public ResumeProjects updateProject(Integer id, ResumeProjectsRequest request) {
        ResumeProjects project = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setTechnologies(request.getTechnologies());
        project.setLink(request.getLink());
        return projectRepo.save(project);
    }

    @Override
    public void deleteProject(Integer id) {
        projectRepo.deleteById(id);
    }

    @Override
    public ResumeCertifications addCertification(Long userId, ResumeCertificationsRequest request) {
        ResumeCertifications certifications = resumeMapper.toEntity(request);
        certifications.setUser(getUser(userId));
        return certificationRepo.save(certifications);
    }

    @Override
    public ResumeCertifications updateCertification(Integer id, ResumeCertificationsRequest request) {
        ResumeCertifications certification = certificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification not found"));
        certification.setName(request.getName());
        certification.setIssuingOrganization(request.getIssuingOrganization());
        certification.setIssueDate(request.getIssueDate());
        certification.setExpiryDate(request.getExpiryDate());
        certification.setCredentialUrl(request.getCredentialUrl());
        return certificationRepo.save(certification);
    }

    @Override
    public void deleteCertification(Integer id) {
        certificationRepo.deleteById(id);
    }

    @Override
    @Transactional
    public ResumeFiles uploadResumeFile(Long userId, org.springframework.web.multipart.MultipartFile file) {
        if (file.isEmpty())
            throw new RuntimeException("File is empty");
        if (file.getSize() > 2 * 1024 * 1024)
            throw new RuntimeException("File size exceeds 2MB");

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") &&
                !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            throw new RuntimeException("Only PDF and DOCX files are allowed");
        }

        ResumeFiles resumeFile = new ResumeFiles();
        resumeFile.setUser(getUser(userId));
        resumeFile.setFileName(file.getOriginalFilename());
        resumeFile.setFileType(contentType);
        try {
            resumeFile.setFileData(file.getBytes());
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to read file data");
        }
        return fileRepo.save(resumeFile);
    }

    @Override
    public ResumeFiles getResumeFile(Long fileId) {
        return fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public void deleteResumeFile(Long fileId) {
        fileRepo.deleteById(fileId);
    }

    @Override
    public List<ResumeSkills> getSkills(Long userId) {
        return skillRepo.findByUserId(userId);
    }

    @Override
    public List<ResumeEducation> getEducation(Long userId) {
        return educationRepo.findByUserId(userId);
    }

    @Override
    public List<ResumeExperience> getExperience(Long userId) {
        return experienceRepo.findByUserId(userId);
    }

    @Override
    public List<ResumeProjects> getProjects(Long userId) {
        return projectRepo.findByUserId(userId);
    }

    @Override
    public List<ResumeCertifications> getCertifications(Long userId) {
        return certificationRepo.findByUserId(userId);
    }

    @Override
    public List<ResumeResponse> searchBySkill(String skillName) {
        String target = skillName == null ? "" : skillName.trim().toLowerCase();
        if (target.isEmpty()) {
            return List.of();
        }

        return skillRepo.findAll().stream()
                .filter(s -> s.getSkillName() != null && s.getSkillName().toLowerCase().contains(target))
                .map(s -> s.getUser().getId())
                .distinct()
                .map(this::getResumeByUserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResumeResponse> searchByLocation(String location) {
        String target = location == null ? "" : location.trim().toLowerCase();
        if (target.isEmpty()) {
            return List.of();
        }

        return userRepository.findAll().stream()
                .filter(u -> u.getLocation() != null && u.getLocation().toLowerCase().contains(target))
                .map(User::getId)
                .distinct()
                .map(this::getResumeByUserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllSeekerSkills() {
        return skillRepo.findAll().stream()
                .map(ResumeSkills::getSkillName)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalResumeCount() {
        return objectiveRepo.count();
    }

    @Override
    @Transactional
    public void clearResume(Long userId) {
        objectiveRepo.findByUserId(userId).ifPresent(objectiveRepo::delete);
        educationRepo.findByUserId(userId).forEach(educationRepo::delete);
        experienceRepo.findByUserId(userId).forEach(experienceRepo::delete);
        skillRepo.findByUserId(userId).forEach(skillRepo::delete);
        projectRepo.findByUserId(userId).forEach(projectRepo::delete);
        certificationRepo.findByUserId(userId).forEach(certificationRepo::delete);
        fileRepo.findByUserId(userId).forEach(fileRepo::delete);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
