package org.example.revhire.model;

import jakarta.persistence.*;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.JobType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private User employer;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private String requiredEducationLevel;

    private String skillsRequired;

    private String location;

    private Integer salaryMin;
    private Integer salaryMax;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private Integer experienceYears;
    private Integer maxExperienceYears;

    private Integer openings;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.OPEN;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobSkill> jobSkills;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobView> jobViews;

    public Job(Long id, User employer, String title, String description, String requirements,
               String requiredEducationLevel, String skillsRequired,
               String location, Integer salaryMin, Integer salaryMax, JobType jobType, Integer experienceYears,
               Integer maxExperienceYears, Integer openings, LocalDate deadline, JobStatus status,
               List<JobSkill> jobSkills,
               List<JobView> jobViews) {
        this.id = id;
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.requiredEducationLevel = requiredEducationLevel;
        this.skillsRequired = skillsRequired;
        this.location = location;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.jobType = jobType;
        this.experienceYears = experienceYears;
        this.maxExperienceYears = maxExperienceYears;
        this.openings = openings;
        this.deadline = deadline;
        this.status = status;
        this.jobSkills = jobSkills;
        this.jobViews = jobViews;
    }

    public Job() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRequiredEducationLevel() {
        return requiredEducationLevel;
    }

    public void setRequiredEducationLevel(String requiredEducationLevel) {
        this.requiredEducationLevel = requiredEducationLevel;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public String location() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Integer getMaxExperienceYears() {
        return maxExperienceYears;
    }

    public void setMaxExperienceYears(Integer maxExperienceYears) {
        this.maxExperienceYears = maxExperienceYears;
    }

    public Integer getOpenings() {
        return openings;
    }

    public void setOpenings(Integer openings) {
        this.openings = openings;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public List<JobSkill> getJobSkills() {
        return jobSkills;
    }

    public void setJobSkills(List<JobSkill> jobSkills) {
        this.jobSkills = jobSkills;
    }

    public List<JobView> getJobViews() {
        return jobViews;
    }

    public void setJobViews(List<JobView> jobViews) {
        this.jobViews = jobViews;
    }

    public java.time.LocalDateTime getPostedAt() {
        return getCreatedAt();
    }
}
