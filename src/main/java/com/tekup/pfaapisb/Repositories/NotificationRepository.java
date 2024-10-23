package com.tekup.pfaapisb.Repositories;

import com.tekup.pfaapisb.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
