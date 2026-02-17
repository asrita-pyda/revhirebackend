package org.example.revhire.repository;

import org.example.revhire.model.ResumeProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeProjectRepository extends JpaRepository<ResumeProjects, Integer> {
    List<ResumeProjects> findByUserId(Long userId);
}
