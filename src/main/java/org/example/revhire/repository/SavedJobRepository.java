package org.example.revhire.repository;

import org.example.revhire.model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {
    List<SavedJob> findByUser_Id(Integer userId);

    Optional<SavedJob> findByUser_IdAndJob_Id(Integer userId, Integer jobId);

    boolean existsByUser_IdAndJob_Id(Integer userId, Integer jobId);
}