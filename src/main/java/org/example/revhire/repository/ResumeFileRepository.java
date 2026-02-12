package org.example.revhire.repository;

import org.example.revhire.model.ResumeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeFileRepository extends JpaRepository<ResumeFiles, Long> {
    List<ResumeFiles> findByUserId(Integer userId);
}
