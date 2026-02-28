package org.example.revhire.controller;

import org.example.revhire.dto.request.*;
import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.response.ResumeResponse;
import org.example.revhire.model.*;
import org.example.revhire.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<ResumeResponse>> getResume(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Resume retrieved", resumeService.getResumeByUserId(userId)));
    }

    @PostMapping("/user/{userId}/objective")
    public ResponseEntity<ApiResponse<ResumeObjective>> saveObjective(@PathVariable Long userId,
                                                                      @RequestBody ResumeObjectiveRequest objective) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Objective saved", resumeService.saveObjective(userId, objective)));
    }

    @PostMapping("/user/{userId}/education")
    public ResponseEntity<ApiResponse<ResumeEducation>> addEducation(@PathVariable Long userId,
                                                                     @RequestBody ResumeEducationRequest education) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Education added", resumeService.addEducation(userId, education)));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<ApiResponse<ResumeEducation>> updateEducation(@PathVariable Integer id,
                                                                        @RequestBody ResumeEducationRequest education) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Education updated", resumeService.updateEducation(id, education)));
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(@PathVariable Integer id) {
        resumeService.deleteEducation(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Education deleted"));
    }

    @PostMapping("/user/{userId}/experience")
    public ResponseEntity<ApiResponse<ResumeExperience>> addExperience(@PathVariable Long userId,
                                                                       @RequestBody ResumeExperienceRequest experience) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Experience added", resumeService.addExperience(userId, experience)));
    }

    @PutMapping("/experience/{id}")
    public ResponseEntity<ApiResponse<ResumeExperience>> updateExperience(@PathVariable Integer id,
                                                                          @RequestBody ResumeExperienceRequest experience) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Experience updated", resumeService.updateExperience(id, experience)));
    }

    @DeleteMapping("/experience/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExperience(@PathVariable Integer id) {
        resumeService.deleteExperience(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Experience deleted"));
    }

    @PostMapping("/user/{userId}/skills")
    public ResponseEntity<ApiResponse<ResumeSkills>> addSkill(@PathVariable Long userId,
                                                              @RequestBody ResumeSkillsRequest skills) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill added", resumeService.addSkill(userId, skills)));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<ResumeSkills>> updateSkill(@PathVariable Integer id,
                                                                 @RequestBody ResumeSkillsRequest skills) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill updated", resumeService.updateSkill(id, skills)));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSkill(@PathVariable Integer id) {
        resumeService.deleteSkill(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill deleted"));
    }

    @PostMapping("/user/{userId}/projects")
    public ResponseEntity<ApiResponse<ResumeProjects>> addProject(@PathVariable Long userId,
                                                                  @RequestBody ResumeProjectsRequest projects) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Project added", resumeService.addProject(userId, projects)));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ApiResponse<ResumeProjects>> updateProject(@PathVariable Integer id,
                                                                     @RequestBody ResumeProjectsRequest projects) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Project updated", resumeService.updateProject(id, projects)));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Integer id) {
        resumeService.deleteProject(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Project deleted"));
    }

    @PostMapping("/user/{userId}/certifications")
    public ResponseEntity<ApiResponse<ResumeCertifications>> addCertification(@PathVariable Long userId,
                                                                              @RequestBody ResumeCertificationsRequest certifications) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Certification added", resumeService.addCertification(userId, certifications)));
    }

    @PutMapping("/certifications/{id}")
    public ResponseEntity<ApiResponse<ResumeCertifications>> updateCertification(@PathVariable Integer id,
                                                                                 @RequestBody ResumeCertificationsRequest certifications) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Certification updated",
                        resumeService.updateCertification(id, certifications)));
    }

    @DeleteMapping("/certifications/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCertification(@PathVariable Integer id) {
        resumeService.deleteCertification(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Certification deleted"));
    }

    @PostMapping(
            value = "/user/{userId}/upload",
            consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<ResumeFiles>> uploadFile(@PathVariable Long userId,
                                                               @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "File uploaded", resumeService.uploadResumeFile(userId, file)));
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        ResumeFiles resumeFiles = resumeService.getResumeFile(fileId);
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resumeFiles.getFileName() + "\"")
                .contentType(org.springframework.http.MediaType.parseMediaType(resumeFiles.getFileType()))
                .body(resumeFiles.getFileData());
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@PathVariable Long fileId) {
        resumeService.deleteResumeFile(fileId);
        return ResponseEntity.ok(new ApiResponse<>(true, "File deleted"));
    }

    @GetMapping("/user/{userId}/skills")
    public ResponseEntity<ApiResponse<java.util.List<ResumeSkills>>> getSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Skills retrieved", resumeService.getSkills(userId)));
    }

    @GetMapping("/user/{userId}/education")
    public ResponseEntity<ApiResponse<java.util.List<ResumeEducation>>> getEducation(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Education retrieved", resumeService.getEducation(userId)));
    }

    @GetMapping("/user/{userId}/experience")
    public ResponseEntity<ApiResponse<java.util.List<ResumeExperience>>> getExperience(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Experience retrieved", resumeService.getExperience(userId)));
    }

    @GetMapping("/user/{userId}/projects")
    public ResponseEntity<ApiResponse<java.util.List<ResumeProjects>>> getProjects(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Projects retrieved", resumeService.getProjects(userId)));
    }

    @GetMapping("/user/{userId}/certifications")
    public ResponseEntity<ApiResponse<java.util.List<ResumeCertifications>>> getCertifications(
            @PathVariable Long userId) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Certifications retrieved", resumeService.getCertifications(userId)));
    }

    @GetMapping("/search/skill")
    public ResponseEntity<ApiResponse<java.util.List<ResumeResponse>>> searchBySkill(@RequestParam String skill) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Resumes by skill retrieved", resumeService.searchBySkill(skill)));
    }

    @GetMapping("/search/location")
    public ResponseEntity<ApiResponse<java.util.List<ResumeResponse>>> searchByLocation(@RequestParam String location) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Resumes by location retrieved", resumeService.searchByLocation(location)));
    }

    @GetMapping("/seeker-skills")
    public ResponseEntity<ApiResponse<java.util.List<String>>> getAllSeekerSkills() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "All seeker skills retrieved", resumeService.getAllSeekerSkills()));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalCount() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Total resumes count retrieved", resumeService.getTotalResumeCount()));
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearResume(@PathVariable Long userId) {
        resumeService.clearResume(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Resume cleared successfully"));
    }
}
