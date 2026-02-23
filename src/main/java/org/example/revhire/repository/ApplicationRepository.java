package org.example.revhire.repository;

import org.example.revhire.model.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, Long> {
    List<Applications> findBySeekerId(Long seekerId);

    List<Applications> findByJobId(Long jobId);
}
