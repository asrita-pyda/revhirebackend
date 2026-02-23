package org.example.revhire.dto.request;

public class ResumeEducationRequest {
    private String degree;
    private String institution;
    private Integer year;
    private String cgpa;

    public ResumeEducationRequest() {
    }

    public ResumeEducationRequest(String degree, String institution, Integer year, String cgpa) {
        this.degree = degree;
        this.institution = institution;
        this.year = year;
        this.cgpa = cgpa;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
}
