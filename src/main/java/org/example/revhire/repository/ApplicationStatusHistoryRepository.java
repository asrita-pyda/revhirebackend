package org.example.revhire.repository;


import org.example.revhire.model.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Integer> {
    List<ApplicationStatusHistory> findByApplicationId(Long applicationId);
}
