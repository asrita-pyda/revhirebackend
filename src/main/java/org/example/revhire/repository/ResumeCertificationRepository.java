package org.example.revhire.repository;

import org.example.revhire.model.ResumeCertifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeCertificationRepository extends JpaRepository<ResumeCertifications, Integer> {
    List<ResumeCertifications> findByUserId(Long userId);
}
