package org.example.revhire.repository;

import org.example.revhire.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUserId(Long userId);

    @Query("select e from Employer e where e.userId = :userId or e.user.id = :userId")
    Optional<Employer> findByUserReferenceId(@Param("userId") Long userId);
}
