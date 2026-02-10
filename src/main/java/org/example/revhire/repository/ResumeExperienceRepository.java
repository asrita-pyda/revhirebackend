package org.example.revhire.repository;

import org.example.revhire.model.ResumeExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeExperienceRepository extends JpaRepository<ResumeExperience, Integer> {
    List<ResumeExperience> findByUser_Id(Integer userId);
}