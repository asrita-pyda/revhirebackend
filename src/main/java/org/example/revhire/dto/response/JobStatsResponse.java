package org.example.revhire.dto.response;

import java.util.Map;

public class JobStatsResponse {
    private Long totalJobs;
    private Long openJobs;
    private Long filledJobs;
    private Map<String, Long> jobsByType;
    private Map<String, Long> jobsByLocation;
    private Map<String, Long> jobsByExperience;

    public JobStatsResponse() {
    }

    public Long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Long getOpenJobs() {
        return openJobs;
    }

    public void setOpenJobs(Long openJobs) {
        this.openJobs = openJobs;
    }

    public Long getFilledJobs() {
        return filledJobs;
    }

    public void setFilledJobs(Long filledJobs) {
        this.filledJobs = filledJobs;
    }

    public Map<String, Long> getJobsByType() {
        return jobsByType;
    }

    public void setJobsByType(Map<String, Long> jobsByType) {
        this.jobsByType = jobsByType;
    }

    public Map<String, Long> getJobsByLocation() {
        return jobsByLocation;
    }

    public void setJobsByLocation(Map<String, Long> jobsByLocation) {
        this.jobsByLocation = jobsByLocation;
    }

    public Map<String, Long> getJobsByExperience() {
        return jobsByExperience;
    }

    public void setJobsByExperience(Map<String, Long> jobsByExperience) {
        this.jobsByExperience = jobsByExperience;
    }
}
