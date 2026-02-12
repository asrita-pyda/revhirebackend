package org.example.revhire.repository;
import org.example.revhire.model.ResumeSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeSkillRepository extends JpaRepository<ResumeSkills, Integer> {
    List<ResumeSkills> findByUserId(Integer userId);
}
