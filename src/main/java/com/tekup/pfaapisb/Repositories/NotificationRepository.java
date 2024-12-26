package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Enum.Role;
import com.tekup.pfaapisb.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> getNotificationsByRole(Role role);
}
