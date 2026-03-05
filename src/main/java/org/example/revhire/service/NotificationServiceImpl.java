package org.example.revhire.service;


import org.example.revhire.model.Notification;
import org.example.revhire.model.User;
import org.example.revhire.repository.NotificationRepository;
import org.example.revhire.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAsUnread(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(Long userId, String message, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId) && !n.isRead())
                .count();
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId) && !n.isRead())
                .forEach(n -> {
                    n.setRead(true);
                    notificationRepository.save(n);
                });
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void clearAllNotifications(Long userId) {
        notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId))
                .forEach(n -> notificationRepository.deleteById(n.getId()));
    }
}
