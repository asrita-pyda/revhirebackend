package org.example.revhire.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class ApplicationRequest {
    @NotNull
    private Long jobId;

    @NotNull
    private Long seekerId;

    private Long resumeFileId;
    private String coverLetter;

    public ApplicationRequest() {
    }

    public ApplicationRequest(Long jobId, Long seekerId, Long resumeFileId, String coverLetter) {
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.resumeFileId = resumeFileId;
        this.coverLetter = coverLetter;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Long seekerId) {
        this.seekerId = seekerId;
    }

    public Long getResumeFileId() {
        return resumeFileId;
    }

    public void setResumeFileId(Long resumeFileId) {
        this.resumeFileId = resumeFileId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ApplicationRequest that = (ApplicationRequest) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(seekerId, that.seekerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, seekerId);
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "jobId=" + jobId +
                ", seekerId=" + seekerId +
                '}';
    }
}
