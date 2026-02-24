package org.example.revhire.service;

import org.example.revhire.dto.response.JobResponse;
import java.util.List;

public interface SavedJobService {
    void saveJob(Long userId, Long jobId);

    void unsaveJob(Long userId, Long jobId);

    List<JobResponse> getSavedJobs(Long userId);
}
