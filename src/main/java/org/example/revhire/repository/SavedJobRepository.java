package org.example.revhire.repository;

import org.example.revhire.model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByUserId(Integer userId);

    Optional<SavedJob> findByUserIdAndJobId(Integer userId, Long jobId);

    boolean existsByUserIdAndJobId(Integer userId, Long jobId);
}