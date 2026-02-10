package org.example.revhire.repository;

import org.example.revhire.model.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, Integer> {
    List<Applications> findBySeeker_Id(Integer seekerId);

    List<Applications> findByJob_Id(Integer jobId);

    Long countByJobEmployerId(Integer employerId);

    Long countByJobEmployerIdAndAppliedAtAfter(Integer employerId, LocalDateTime date);

    Optional<Applications> findByJob_IdAndSeeker_Id(Integer jobId, Integer seekerId);

    boolean existsByJob_IdAndSeeker_Id(Integer jobId, Integer seekerId);
}
