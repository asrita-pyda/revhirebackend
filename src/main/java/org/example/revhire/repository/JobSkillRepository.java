package org.example.revhire.repository;



import org.example.revhire.model.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Integer> {
    List<JobSkill> findByJobId(Long jobId);
}