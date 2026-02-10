package org.example.revhire.repository;

import org.example.revhire.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser_IdOrderByCreatedAtDesc(Integer userId);

    List<Notification> findByUser_IdAndIsReadFalse(Integer userId);
}