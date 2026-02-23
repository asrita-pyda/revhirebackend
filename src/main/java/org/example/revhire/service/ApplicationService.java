package org.example.revhire.service;

import org.example.revhire.dto.request.ApplicationRequest;
import org.example.revhire.dto.request.BulkStatusUpdateRequest;
import org.example.revhire.dto.response.ApplicationResponse;
import org.example.revhire.model.ApplicationStatusHistory;
import org.example.revhire.model.WithdrawalReasons;
import org.example.revhire.model.ApplicationNotes;
import org.example.revhire.enums.ApplicationStatus;


import java.util.List;

public interface ApplicationService {
    ApplicationResponse applyForJob(ApplicationRequest request);

    List<ApplicationResponse> getApplicationsBySeeker(Long seekerId);

    List<ApplicationResponse> getApplicationsByJob(Long jobId);

    ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatus status);

    void withdrawApplication(Long applicationId, String reason);

    void addNote(Long applicationId, String note);

    List<ApplicationNotes> getNotes(Long applicationId);

    void bulkUpdateStatus(BulkStatusUpdateRequest request);

    ApplicationResponse getFullApplicationDetails(Long id);

    List<ApplicationStatusHistory> getApplicationStatusHistory(Long applicationId);

    void deleteApplicationNote(Long noteId);

    List<ApplicationResponse> getActiveApplications(Long seekerId);

    List<ApplicationResponse> getApplicationsByJobAndStatus(Long jobId, ApplicationStatus status);

    long getTotalApplicationCount();

    long getTodayApplicationCount();

    List<ApplicationResponse> searchApplications(String query);

    List<WithdrawalReasons> getWithdrawalReasons();
}
