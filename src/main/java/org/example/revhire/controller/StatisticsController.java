package org.example.revhire.controller;

import org.example.revhire.dto.response.ApiResponse;
import org.example.revhire.dto.response.EmployerStatsResponse;
import org.example.revhire.dto.response.PlatformStatsResponse;
import org.example.revhire.dto.response.JobStatsResponse;
import org.example.revhire.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/employer/{id}")
    public ResponseEntity<ApiResponse<EmployerStatsResponse>> getEmployerStats(@PathVariable Long id) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Employer statistics retrieved", statisticsService.getEmployerStats(id)));
    }

    @GetMapping("/platform")
    public ResponseEntity<ApiResponse<PlatformStatsResponse>> getPlatformOverview() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Platform overview retrieved", statisticsService.getPlatformOverview()));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<JobStatsResponse>> getJobAnalytics() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Job analytics retrieved", statisticsService.getJobAnalytics()));
    }

    @GetMapping("/trends")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getApplicationTrends() {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Application trends retrieved", statisticsService.getApplicationTrends()));
    }

    @GetMapping("/activity/employer/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getEmployerActivity(@PathVariable Long id) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Employer activity retrieved", statisticsService.getEmployerActivity(id)));
    }

    @GetMapping("/engagement/seeker/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSeekerEngagement(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Seeker engagement stats retrieved",
                statisticsService.getSeekerEngagement(id)));
    }
}
