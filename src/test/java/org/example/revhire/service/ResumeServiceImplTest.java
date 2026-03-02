package org.example.revhire.service;

import org.example.revhire.dto.request.*;
import org.example.revhire.model.*;
import org.example.revhire.repository.*;
import org.example.revhire.mapper.ResumeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.example.revhire.dto.response.ResumeResponse;
import org.example.revhire.dto.response.ResumeObjectiveResponse;

class ResumeServiceImplTest {

    @Mock
    private ResumeObjectiveRepository objectiveRepo;
    @Mock
    private ResumeEducationRepository educationRepo;
    @Mock
    private ResumeExperienceRepository experienceRepo;
    @Mock
    private ResumeSkillRepository skillRepo;
    @Mock
    private ResumeProjectRepository projectRepo;
    @Mock
    private ResumeCertificationRepository certificationRepo;
    @Mock
    private ResumeFileRepository fileRepo;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ResumeMapper resumeMapper;

    @InjectMocks
    private ResumeServiceImpl resumeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getResumeByUserId_Success() {
        when(objectiveRepo.findByUserId(1L)).thenReturn(Optional.of(new ResumeObjective()));
        when(educationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(experienceRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(skillRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(projectRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(certificationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(fileRepo.findByUserId(1L)).thenReturn(new ArrayList<>());

        when(resumeMapper.toDto(any(ResumeObjective.class))).thenReturn(new ResumeObjectiveResponse());

        ResumeResponse result = resumeService.getResumeByUserId(1L);
        assertNotNull(result);
    }

    @Test
    void saveObjective_Success() {
        ResumeObjectiveRequest req = new ResumeObjectiveRequest();
        req.setObjective("To be a great developer");

        User user = new User();
        user.setId(1L);

        ResumeObjective objective = new ResumeObjective();
        objective.setObjective("To be a great developer");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(objectiveRepo.findByUserId(1L)).thenReturn(Optional.empty());
        when(objectiveRepo.save(any(ResumeObjective.class))).thenReturn(objective);

        ResumeObjective response = resumeService.saveObjective(1L, req);

        assertNotNull(response);
        assertEquals("To be a great developer", response.getObjective());
        verify(objectiveRepo).save(any(ResumeObjective.class));
    }

    @Test
    void addEducation_Success() {
        ResumeEducationRequest req = new ResumeEducationRequest();
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(resumeMapper.toEntity(req)).thenReturn(new ResumeEducation());
        when(educationRepo.save(any())).thenReturn(new ResumeEducation());

        resumeService.addEducation(1L, req);
        verify(educationRepo).save(any());
    }

    @Test
    void updateEducation_Success() {
        ResumeEducation edu = new ResumeEducation();
        when(educationRepo.findById(1)).thenReturn(Optional.of(edu));
        when(educationRepo.save(any())).thenReturn(edu);

        resumeService.updateEducation(1, new ResumeEducationRequest());
        verify(educationRepo).save(any());
    }

    @Test
    void deleteEducation_Success() {
        resumeService.deleteEducation(1);
        verify(educationRepo).deleteById(1);
    }

    @Test
    void addExperience_Success() {
        ResumeExperienceRequest req = new ResumeExperienceRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(resumeMapper.toEntity(req)).thenReturn(new ResumeExperience());
        resumeService.addExperience(1L, req);
        verify(experienceRepo).save(any());
    }

    @Test
    void updateExperience_Success() {
        ResumeExperience exp = new ResumeExperience();
        when(experienceRepo.findById(1)).thenReturn(Optional.of(exp));
        resumeService.updateExperience(1, new ResumeExperienceRequest());
        verify(experienceRepo).save(any());
    }

    @Test
    void addSkill_Success() {
        ResumeSkillsRequest req = new ResumeSkillsRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(resumeMapper.toEntity(req)).thenReturn(new ResumeSkills());
        resumeService.addSkill(1L, req);
        verify(skillRepo).save(any());
    }

    @Test
    void updateSkill_Success() {
        ResumeSkills skill = new ResumeSkills();
        when(skillRepo.findById(1)).thenReturn(Optional.of(skill));
        resumeService.updateSkill(1, new ResumeSkillsRequest());
        verify(skillRepo).save(any());
    }

    @Test
    void addProject_Success() {
        ResumeProjectsRequest req = new ResumeProjectsRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(resumeMapper.toEntity(req)).thenReturn(new ResumeProjects());
        resumeService.addProject(1L, req);
        verify(projectRepo).save(any());
    }

    @Test
    void updateProject_Success() {
        ResumeProjects proj = new ResumeProjects();
        when(projectRepo.findById(1)).thenReturn(Optional.of(proj));
        resumeService.updateProject(1, new ResumeProjectsRequest());
        verify(projectRepo).save(any());
    }

    @Test
    void addCertification_Success() {
        ResumeCertificationsRequest req = new ResumeCertificationsRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(resumeMapper.toEntity(req)).thenReturn(new ResumeCertifications());
        resumeService.addCertification(1L, req);
        verify(certificationRepo).save(any());
    }

    @Test
    void updateCertification_Success() {
        ResumeCertifications cert = new ResumeCertifications();
        when(certificationRepo.findById(1)).thenReturn(Optional.of(cert));
        resumeService.updateCertification(1, new ResumeCertificationsRequest());
        verify(certificationRepo).save(any());
    }

    @Test
    void uploadResumeFile_Success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(1024L);
        when(file.getContentType()).thenReturn("application/pdf");
        when(file.getBytes()).thenReturn(new byte[10]);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        resumeService.uploadResumeFile(1L, file);
        verify(fileRepo).save(any());
    }

    @Test
    void uploadResumeFile_Empty() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        assertThrows(RuntimeException.class, () -> resumeService.uploadResumeFile(1L, file));
    }

    @Test
    void getResumeFile_Success() {
        when(fileRepo.findById(1L)).thenReturn(Optional.of(new ResumeFiles()));
        resumeService.getResumeFile(1L);
        verify(fileRepo).findById(1L);
    }

    @Test
    void lists_Success() {
        when(skillRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.getSkills(1L));

        when(educationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.getEducation(1L));

        when(experienceRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.getExperience(1L));

        when(projectRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.getProjects(1L));

        when(certificationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.getCertifications(1L));
    }

    @Test
    void getAllSeekerSkills_Success() {
        ResumeSkills s1 = new ResumeSkills();
        s1.setSkillName("Java");
        when(skillRepo.findAll()).thenReturn(List.of(s1));
        List<String> result = resumeService.getAllSeekerSkills();
        assertEquals(1, result.size());
    }

    @Test
    void clearResume_Success() {
        ResumeObjective obj = new ResumeObjective();
        when(objectiveRepo.findByUserId(1L)).thenReturn(Optional.of(obj));
        when(educationRepo.findByUserId(1L)).thenReturn(List.of(new ResumeEducation()));

        resumeService.clearResume(1L);

        verify(objectiveRepo).delete(obj);
        verify(educationRepo).delete(any());
    }

    @Test
    void deleteExperience_Success() {
        resumeService.deleteExperience(1);
        verify(experienceRepo).deleteById(1);
    }

    @Test
    void deleteSkill_Success() {
        resumeService.deleteSkill(1);
        verify(skillRepo).deleteById(1);
    }

    @Test
    void deleteProject_Success() {
        resumeService.deleteProject(1);
        verify(projectRepo).deleteById(1);
    }

    @Test
    void deleteCertification_Success() {
        resumeService.deleteCertification(1);
        verify(certificationRepo).deleteById(1);
    }

    @Test
    void deleteResumeFile_Success() {
        resumeService.deleteResumeFile(1L);
        verify(fileRepo).deleteById(1L);
    }

    @Test
    void searchBySkill_Success() {
        when(skillRepo.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.searchBySkill("Java"));
    }

    @Test
    void searchByLocation_Success() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(resumeService.searchByLocation("New York"));
    }

    @Test
    void getTotalResumeCount_Success() {
        when(objectiveRepo.count()).thenReturn(10L);
        assertEquals(10L, resumeService.getTotalResumeCount());
    }

    @Test
    void getUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.addEducation(1L, new ResumeEducationRequest()));
    }

    // --- Additional Edge Case Tests ---

    @Test
    void uploadResumeFile_TooLarge_Throws() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(3L * 1024 * 1024); // 3MB, exceeds 2MB limit
        assertThrows(RuntimeException.class, () -> resumeService.uploadResumeFile(1L, file));
    }

    @Test
    void uploadResumeFile_InvalidContentType_Throws() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(1024L);
        when(file.getContentType()).thenReturn("image/jpeg");
        assertThrows(RuntimeException.class, () -> resumeService.uploadResumeFile(1L, file));
    }

    @Test
    void uploadResumeFile_NullContentType_Throws() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(1024L);
        when(file.getContentType()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> resumeService.uploadResumeFile(1L, file));
    }

    @Test
    void uploadResumeFile_Docx_Success() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getSize()).thenReturn(1024L);
        when(file.getContentType())
                .thenReturn("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        when(file.getBytes()).thenReturn(new byte[10]);
        when(file.getOriginalFilename()).thenReturn("resume.docx");
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        resumeService.uploadResumeFile(1L, file);
        verify(fileRepo).save(any());
    }

    @Test
    void getResumeFile_NotFound_Throws() {
        when(fileRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.getResumeFile(99L));
    }

    @Test
    void updateEducation_NotFound_Throws() {
        when(educationRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.updateEducation(99, new ResumeEducationRequest()));
    }

    @Test
    void updateExperience_NotFound_Throws() {
        when(experienceRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.updateExperience(99, new ResumeExperienceRequest()));
    }

    @Test
    void updateSkill_NotFound_Throws() {
        when(skillRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.updateSkill(99, new ResumeSkillsRequest()));
    }

    @Test
    void updateProject_NotFound_Throws() {
        when(projectRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> resumeService.updateProject(99, new ResumeProjectsRequest()));
    }

    @Test
    void updateCertification_NotFound_Throws() {
        when(certificationRepo.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> resumeService.updateCertification(99, new ResumeCertificationsRequest()));
    }

    @Test
    void saveObjective_ExistingObjective_Updates() {
        ResumeObjectiveRequest req = new ResumeObjectiveRequest();
        req.setObjective("Updated objective");
        User user = new User();
        user.setId(1L);
        ResumeObjective existing = new ResumeObjective();
        existing.setObjective("Old objective");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(objectiveRepo.findByUserId(1L)).thenReturn(Optional.of(existing));
        when(objectiveRepo.save(any())).thenReturn(existing);

        ResumeObjective result = resumeService.saveObjective(1L, req);
        assertEquals("Updated objective", result.getObjective());
    }

    @Test
    void getResumeByUserId_NoObjective() {
        when(objectiveRepo.findByUserId(1L)).thenReturn(Optional.empty());
        when(educationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(experienceRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(skillRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(projectRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(certificationRepo.findByUserId(1L)).thenReturn(new ArrayList<>());
        when(fileRepo.findByUserId(1L)).thenReturn(new ArrayList<>());

        ResumeResponse result = resumeService.getResumeByUserId(1L);
        assertNotNull(result);
    }
}
