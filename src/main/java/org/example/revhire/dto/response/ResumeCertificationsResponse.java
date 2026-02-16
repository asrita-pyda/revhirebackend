package org.example.revhire.dto.response;


public class ResumeCertificationsResponse {
    private Integer id;
    private String certificateName;
    private String organization;
    private Integer year;

    public ResumeCertificationsResponse() {
    }

    public ResumeCertificationsResponse(Integer id, String certificateName, String organization, Integer year) {
        this.id = id;
        this.certificateName = certificateName;
        this.organization = organization;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
