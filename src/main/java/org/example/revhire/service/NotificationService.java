package org.example.revhire.service;


import org.example.revhire.model.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getUserNotifications(Long userId);

    List<Notification> getUnreadNotifications(Long userId);

    void markAsRead(Long notificationId);

    void createNotification(Long userId, String message, String type);

    long getUnreadCount(Long userId);

    void markAllAsRead(Long userId);

    void deleteNotification(Long id);

    void clearAllNotifications(Long userId);
}

