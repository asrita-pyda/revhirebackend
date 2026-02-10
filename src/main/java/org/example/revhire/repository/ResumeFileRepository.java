package org.example.revhire.repository;

import org.example.revhire.model.ResumeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeFileRepository extends JpaRepository<ResumeFiles, Integer> {
    List<ResumeFiles> findByUser_Id(Integer userId);
}
