package org.example.revhire.dto.request;

public class WithdrawalReasonRequest {
    private String reason;

    public WithdrawalReasonRequest() {
    }

    public WithdrawalReasonRequest(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}



