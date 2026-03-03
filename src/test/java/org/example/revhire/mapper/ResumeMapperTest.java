package org.example.revhire.mapper;

import org.example.revhire.dto.request.*;
import org.example.revhire.dto.response.*;
import org.example.revhire.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumeMapperTest {

    private final ResumeMapper resumeMapper = ResumeMapper.INSTANCE;

    @Test
    void toDto_Success() {
        ResumeObjective objective = new ResumeObjective();
        objective.setId(1);
        objective.setObjective("To be a great developer");

        ResumeObjectiveResponse dto = resumeMapper.toDto(objective);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("To be a great developer", dto.getObjective());
    }

    @Test
    void educationMapping_Success() {
        ResumeEducationRequest req = new ResumeEducationRequest();
        req.setInstitution("University");
        ResumeEducation entity = resumeMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("University", entity.getInstitution());

        ResumeEducationResponse dto = resumeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals("University", dto.getInstitution());
    }

    @Test
    void experienceMapping_Success() {
        ResumeExperienceRequest req = new ResumeExperienceRequest();
        req.setCompany("Company");
        ResumeExperience entity = resumeMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("Company", entity.getCompany());

        ResumeExperienceResponse dto = resumeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals("Company", dto.getCompany());
    }

    @Test
    void skillMapping_Success() {
        ResumeSkillsRequest req = new ResumeSkillsRequest();
        req.setSkillName("Java");
        ResumeSkills entity = resumeMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("Java", entity.getSkillName());

        ResumeSkillsResponse dto = resumeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals("Java", dto.getSkillName());
    }

    @Test
    void projectMapping_Success() {
        ResumeProjectsRequest req = new ResumeProjectsRequest();
        req.setTitle("Project");
        ResumeProjects entity = resumeMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("Project", entity.getTitle());

        ResumeProjectsResponse dto = resumeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals("Project", dto.getTitle());
    }

    @Test
    void certificationMapping_Success() {
        ResumeCertificationsRequest req = new ResumeCertificationsRequest();
        req.setName("Cert");
        ResumeCertifications entity = resumeMapper.toEntity(req);
        assertNotNull(entity);
        assertEquals("Cert", entity.getName());

        ResumeCertificationsResponse dto = resumeMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals("Cert", dto.getName());
    }

    @Test
    void fileMapping_Success() {
        ResumeFiles file = new ResumeFiles();
        file.setFileName("resume.pdf");
        ResumeFilesResponse dto = resumeMapper.toDto(file);
        assertNotNull(dto);
        assertEquals("resume.pdf", dto.getFileName());
    }

    @Test
    void listMappings_Success() {
        assertNotNull(resumeMapper.toObjectiveDtoList(List.of(new ResumeObjective())));
        assertNotNull(resumeMapper.toEducationDtoList(List.of(new ResumeEducation())));
        assertNotNull(resumeMapper.toExperienceDtoList(List.of(new ResumeExperience())));
        assertNotNull(resumeMapper.toSkillsDtoList(List.of(new ResumeSkills())));
        assertNotNull(resumeMapper.toProjectsDtoList(List.of(new ResumeProjects())));
        assertNotNull(resumeMapper.toCertificationsDtoList(List.of(new ResumeCertifications())));
        assertNotNull(resumeMapper.toFilesDtoList(List.of(new ResumeFiles())));
    }

    @Test
    void nullMappings_Success() {
        assertNull(resumeMapper.toEntity((ResumeObjectiveRequest) null));
        assertNull(resumeMapper.toDto((ResumeObjective) null));
        assertNull(resumeMapper.toObjectiveDtoList(null));
        assertNull(resumeMapper.toEducationDtoList(null));
        assertNull(resumeMapper.toExperienceDtoList(null));
        assertNull(resumeMapper.toSkillsDtoList(null));
        assertNull(resumeMapper.toProjectsDtoList(null));
        assertNull(resumeMapper.toCertificationsDtoList(null));
        assertNull(resumeMapper.toFilesDtoList(null));
    }
}
