package org.example.revhire.service;

import org.example.revhire.dto.response.*;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    EmployerStatsResponse getEmployerStats(Long employerId);

    PlatformStatsResponse getPlatformOverview();

    JobStatsResponse getJobAnalytics();

    List<Map<String, Object>> getApplicationTrends();

    Map<String, Object> getEmployerActivity(Long employerId);

    Map<String, Object> getSeekerEngagement(Long userId);
}
