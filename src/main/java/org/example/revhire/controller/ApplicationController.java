package org.example.revhire.controller;


import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.request.ApplicationRequest;
import org.example.revhire.dto.request.BulkStatusUpdateRequest;
import org.example.revhire.dto.request.WithdrawalReasonRequest;
import org.example.revhire.dto.response.ApplicationResponse;
import org.example.revhire.enums.ApplicationStatus;
import org.example.revhire.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationResponse>> applyForJob(
            @Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Application submitted", applicationService.applyForJob(request)));
    }

    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getApplicationsBySeeker(
            @PathVariable Long seekerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Applications retrieved",
                applicationService.getApplicationsBySeeker(seekerId)));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Applications retrieved", applicationService.getApplicationsByJob(jobId)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Status updated", applicationService.updateApplicationStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> withdrawApplication(@PathVariable Long id,
                                                                 @org.springframework.web.bind.annotation.RequestBody(required = false)
                                                                 WithdrawalReasonRequest payload) {
        String reason = payload != null ? payload.getReason() : null;
        applicationService.withdrawApplication(id, reason);
        return ResponseEntity.ok(new ApiResponse<>(true, "Application withdrawn"));
    }

    @PostMapping("/{id}/notes")
    public ResponseEntity<ApiResponse<Void>> addNote(@PathVariable Long id, @RequestBody String note) {
        applicationService.addNote(id, note);
        return ResponseEntity.ok(new ApiResponse<>(true, "Note added"));
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<ApiResponse<List<org.example.revhire.model.ApplicationNotes>>> getNotes(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Notes retrieved", applicationService.getNotes(id)));
    }

    @PostMapping("/bulk-status")
    public ResponseEntity<ApiResponse<Void>> bulkUpdateStatus(
            @RequestBody BulkStatusUpdateRequest request) {
        applicationService.bulkUpdateStatus(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bulk status updated"));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getFullDetails(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Full details retrieved", applicationService.getFullApplicationDetails(id)));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<org.example.revhire.model.ApplicationStatusHistory>>> getStatusHistory(
            @PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Status history retrieved",
                applicationService.getApplicationStatusHistory(id)));
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long noteId) {
        applicationService.deleteApplicationNote(noteId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Note deleted"));
    }

    @GetMapping("/active/seeker/{seekerId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getActiveApplications(
            @PathVariable Long seekerId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Active applications retrieved",
                applicationService.getActiveApplications(seekerId)));
    }

    @GetMapping("/job/{jobId}/status/{status}")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getByJobAndStatus(@PathVariable Long jobId,
                                                                                    @PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Applications filtered by job and status",
                applicationService.getApplicationsByJobAndStatus(jobId, status)));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getTotalCount() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Total applications count retrieved",
                applicationService.getTotalApplicationCount()));
    }

    @GetMapping("/count/today")
    public ResponseEntity<ApiResponse<Long>> getTodayCount() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Today's applications count retrieved",
                applicationService.getTodayApplicationCount()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> searchApplications(@RequestParam String query) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Search results", applicationService.searchApplications(query)));
    }

    @GetMapping("/withdrawal-reasons")
    public ResponseEntity<ApiResponse<List<org.example.revhire.model.WithdrawalReasons>>> getWithdrawalReasons() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Withdrawal reasons retrieved", applicationService.getWithdrawalReasons()));
    }
}