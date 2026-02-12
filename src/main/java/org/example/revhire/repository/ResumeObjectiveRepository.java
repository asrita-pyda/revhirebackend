package org.example.revhire.repository;

import org.example.revhire.model.ResumeObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeObjectiveRepository extends JpaRepository<ResumeObjective, Integer> {
    Optional<ResumeObjective> findByUserId(Integer userId);
}
