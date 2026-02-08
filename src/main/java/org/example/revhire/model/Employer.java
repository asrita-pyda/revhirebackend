package org.example.revhire.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employers")
public class Employer {

    @Id
    private Integer userId;

    private String companyName;
    private String industry;
    private String companySize;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String website;
    private String location;


    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;


    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
