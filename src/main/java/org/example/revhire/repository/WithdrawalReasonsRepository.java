package org.example.revhire.repository;

import org.example.revhire.model.WithdrawalReasons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalReasonsRepository extends JpaRepository<WithdrawalReasons, Integer> {
}
