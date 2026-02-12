package org.example.revhire.repository;

import org.example.revhire.model.ResumeEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeEducationRepository extends JpaRepository<ResumeEducation, Integer> {
    List<ResumeEducation> findByUserId(Integer userId);
}
