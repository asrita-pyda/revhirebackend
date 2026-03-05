package org.example.revhire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.revhire.dto.request.*;
import org.example.revhire.dto.response.ResumeResponse;
import org.example.revhire.model.*;
import org.example.revhire.service.ResumeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResumeController.class)
@AutoConfigureMockMvc(addFilters = false)
class ResumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResumeService resumeService;

    @MockBean
    private org.example.revhire.config.JwtUtils jwtUtils;

    @MockBean
    private org.example.revhire.repository.UserRepository userRepository;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getResume_Success() throws Exception {
        when(resumeService.getResumeByUserId(1L)).thenReturn(new ResumeResponse());
        mockMvc.perform(get("/api/resumes/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void saveObjective_Success() throws Exception {
        ResumeObjectiveRequest req = new ResumeObjectiveRequest();
        when(resumeService.saveObjective(eq(1L), any())).thenReturn(new ResumeObjective());
        mockMvc.perform(post("/api/resumes/user/1/objective")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void addEducation_Success() throws Exception {
        ResumeEducationRequest req = new ResumeEducationRequest();
        when(resumeService.addEducation(eq(1L), any())).thenReturn(new ResumeEducation());
        mockMvc.perform(post("/api/resumes/user/1/education")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateEducation_Success() throws Exception {
        ResumeEducationRequest req = new ResumeEducationRequest();
        when(resumeService.updateEducation(eq(1), any())).thenReturn(new ResumeEducation());
        mockMvc.perform(put("/api/resumes/education/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEducation_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/education/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addExperience_Success() throws Exception {
        ResumeExperienceRequest req = new ResumeExperienceRequest();
        when(resumeService.addExperience(eq(1L), any())).thenReturn(new ResumeExperience());
        mockMvc.perform(post("/api/resumes/user/1/experience")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateExperience_Success() throws Exception {
        ResumeExperienceRequest req = new ResumeExperienceRequest();
        when(resumeService.updateExperience(eq(1), any())).thenReturn(new ResumeExperience());
        mockMvc.perform(put("/api/resumes/experience/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteExperience_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/experience/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addSkill_Success() throws Exception {
        ResumeSkillsRequest req = new ResumeSkillsRequest();
        when(resumeService.addSkill(eq(1L), any())).thenReturn(new ResumeSkills());
        mockMvc.perform(post("/api/resumes/user/1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateSkill_Success() throws Exception {
        ResumeSkillsRequest req = new ResumeSkillsRequest();
        when(resumeService.updateSkill(eq(1), any())).thenReturn(new ResumeSkills());
        mockMvc.perform(put("/api/resumes/skills/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSkill_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/skills/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addProject_Success() throws Exception {
        ResumeProjectsRequest req = new ResumeProjectsRequest();
        when(resumeService.addProject(eq(1L), any())).thenReturn(new ResumeProjects());
        mockMvc.perform(post("/api/resumes/user/1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProject_Success() throws Exception {
        ResumeProjectsRequest req = new ResumeProjectsRequest();
        when(resumeService.updateProject(eq(1), any())).thenReturn(new ResumeProjects());
        mockMvc.perform(put("/api/resumes/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProject_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/projects/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addCertification_Success() throws Exception {
        ResumeCertificationsRequest req = new ResumeCertificationsRequest();
        when(resumeService.addCertification(eq(1L), any())).thenReturn(new ResumeCertifications());
        mockMvc.perform(post("/api/resumes/user/1/certifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void updateCertification_Success() throws Exception {
        ResumeCertificationsRequest req = new ResumeCertificationsRequest();
        when(resumeService.updateCertification(eq(1), any())).thenReturn(new ResumeCertifications());
        mockMvc.perform(put("/api/resumes/certifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCertification_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/certifications/1"))
                .andExpect(status().isOk());
    }

    @Test
    void uploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes());
        when(resumeService.uploadResumeFile(eq(1L), any())).thenReturn(new ResumeFiles());
        mockMvc.perform(multipart("/api/resumes/user/1/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void downloadFile_Success() throws Exception {
        ResumeFiles file = new ResumeFiles();
        file.setFileName("test.pdf");
        file.setFileType("application/pdf");
        file.setFileData("test".getBytes());
        when(resumeService.getResumeFile(1L)).thenReturn(file);
        mockMvc.perform(get("/api/resumes/file/1"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.pdf\""));
    }

    @Test
    void deleteFile_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/file/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getSkills_Success() throws Exception {
        when(resumeService.getSkills(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/user/1/skills"))
                .andExpect(status().isOk());
    }

    @Test
    void getEducation_Success() throws Exception {
        when(resumeService.getEducation(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/user/1/education"))
                .andExpect(status().isOk());
    }

    @Test
    void getExperience_Success() throws Exception {
        when(resumeService.getExperience(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/user/1/experience"))
                .andExpect(status().isOk());
    }

    @Test
    void getProjects_Success() throws Exception {
        when(resumeService.getProjects(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/user/1/projects"))
                .andExpect(status().isOk());
    }

    @Test
    void getCertifications_Success() throws Exception {
        when(resumeService.getCertifications(1L)).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/user/1/certifications"))
                .andExpect(status().isOk());
    }

    @Test
    void searchBySkill_Success() throws Exception {
        when(resumeService.searchBySkill("Java")).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/search/skill").param("skill", "Java"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByLocation_Success() throws Exception {
        when(resumeService.searchByLocation("NYC")).thenReturn(List.of());
        mockMvc.perform(get("/api/resumes/search/location").param("location", "NYC"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSeekerSkills_Success() throws Exception {
        when(resumeService.getAllSeekerSkills()).thenReturn(List.of("Java"));
        mockMvc.perform(get("/api/resumes/seeker-skills"))
                .andExpect(status().isOk());
    }

    @Test
    void getTotalCount_Success() throws Exception {
        when(resumeService.getTotalResumeCount()).thenReturn(10L);
        mockMvc.perform(get("/api/resumes/count"))
                .andExpect(status().isOk());
    }

    @Test
    void clearResume_Success() throws Exception {
        mockMvc.perform(delete("/api/resumes/user/1/clear"))
                .andExpect(status().isOk());
    }
}

