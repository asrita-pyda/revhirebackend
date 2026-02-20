package org.example.revhire.dto.response;


public class ResumeCertificationsResponse {
    private Integer id;
    private String name;
    private String issuingOrganization;
    private java.time.LocalDate issueDate;
    private java.time.LocalDate expiryDate;
    private String credentialUrl;

    public ResumeCertificationsResponse() {
    }

    public ResumeCertificationsResponse(Integer id, String name, String issuingOrganization,
                                        java.time.LocalDate issueDate, java.time.LocalDate expiryDate,
                                        String credentialUrl) {
        this.id = id;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.credentialUrl = credentialUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public java.time.LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(java.time.LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public java.time.LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(java.time.LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }
}
