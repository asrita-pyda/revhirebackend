package org.example.revhire.repository;

import org.example.revhire.model.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, Long> {

    List<Applications> findBySeekerId(Integer seekerId);

    List<Applications> findByJobId(Long jobId);

    Long countByJobEmployerId(Integer employerId);

    Long countByJobEmployerIdAndAppliedAtAfter(Integer employerId, LocalDateTime date);

    Optional<Applications> findByJobIdAndSeekerId(Long jobId, Integer seekerId);

    boolean existsByJobIdAndSeekerId(Long jobId, Integer seekerId);
}
