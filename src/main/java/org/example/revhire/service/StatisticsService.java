package org.example.revhire.service;

import org.example.revhire.dto.response.*;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    EmployerStatsResponse getEmployerStats(Integer employerId);

    PlatformStatsResponse getPlatformOverview();

    JobStatsResponse getJobAnalytics();

    List<Map<String, Object>> getApplicationTrends();

    Map<String, Object> getEmployerActivity(Integer employerId);

    Map<String, Object> getSeekerEngagement(Integer userId);
}
