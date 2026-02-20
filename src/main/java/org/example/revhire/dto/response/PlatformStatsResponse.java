package org.example.revhire.dto.response;

import java.util.Map;

public class PlatformStatsResponse {
    private Long totalUsers;
    private Long totalJobs;
    private Long totalApplications;
    private Long totalResumes;
    private Map<String, Long> usersByRole;
    private Map<String, Long> jobsByStatus;
    private Long todayApplications;

    public PlatformStatsResponse() {
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getTotalResumes() {
        return totalResumes;
    }

    public void setTotalResumes(Long totalResumes) {
        this.totalResumes = totalResumes;
    }

    public Map<String, Long> getUsersByRole() {
        return usersByRole;
    }

    public void setUsersByRole(Map<String, Long> usersByRole) {
        this.usersByRole = usersByRole;
    }

    public Map<String, Long> getJobsByStatus() {
        return jobsByStatus;
    }

    public void setJobsByStatus(Map<String, Long> jobsByStatus) {
        this.jobsByStatus = jobsByStatus;
    }

    public Long getTodayApplications() {
        return todayApplications;
    }

    public void setTodayApplications(Long todayApplications) {
        this.todayApplications = todayApplications;
    }
}
