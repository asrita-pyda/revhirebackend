package org.example.revhire.dto.request;

public class ResumeCertificationsRequest {
    private String certificateName;
    private String organization;
    private Integer year;

    public ResumeCertificationsRequest() {
    }

    public ResumeCertificationsRequest(String certificateName, String organization, Integer year) {
        this.certificateName = certificateName;
        this.organization = organization;
        this.year = year;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
