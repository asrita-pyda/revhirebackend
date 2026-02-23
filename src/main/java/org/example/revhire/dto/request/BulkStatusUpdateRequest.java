package org.example.revhire.dto.request;

import org.example.revhire.enums.ApplicationStatus;
import java.util.List;

public class BulkStatusUpdateRequest {
    private List<Long> applicationIds;
    private ApplicationStatus status;

    public BulkStatusUpdateRequest() {
    }

    public List<Long> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Long> applicationIds) {
        this.applicationIds = applicationIds;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}

