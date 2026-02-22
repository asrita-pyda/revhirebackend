package org.example.revhire.controller;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.service.SavedJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
@CrossOrigin(origins = "*")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping("/{userId}/{jobId}")
    public ResponseEntity<Void> saveJob(@PathVariable Long userId, @PathVariable Long jobId) {
        savedJobService.saveJob(userId, jobId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{jobId}")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long userId, @PathVariable Long jobId) {
        savedJobService.unsaveJob(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<JobResponse>> getSavedJobs(@PathVariable Long userId) {
        return ResponseEntity.ok(savedJobService.getSavedJobs(userId));
    }
}
