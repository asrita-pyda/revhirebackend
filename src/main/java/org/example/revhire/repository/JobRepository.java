package org.example.revhire.repository;

import org.example.revhire.model.Job;
import org.example.revhire.enums.JobStatus;
import org.example.revhire.enums.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContaining(String title);

    List<Job> findByLocationContaining(String location);

    List<Job> findByJobType(JobType jobType);

    @Query("SELECT j FROM Job j WHERE " +
            "(:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND "
            +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:jobType IS NULL OR j.jobType = :jobType) AND " +
            "j.status = 'OPEN' ORDER BY j.postedAt DESC")
    List<Job> findByCombinedFilters(@Param("keyword") String keyword,
                                    @Param("location") String location,
                                    @Param("jobType") JobType jobType);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByEmployerId(Long employerId);

}
