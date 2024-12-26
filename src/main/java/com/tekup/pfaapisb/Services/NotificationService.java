package com.tekup.pfaapisb.Services;

import com.tekup.pfaapisb.DTO.NotificationDTO;
import com.tekup.pfaapisb.Enum.Role;
import com.tekup.pfaapisb.Models.Notification;
import com.tekup.pfaapisb.Models.UserEntity;
import com.tekup.pfaapisb.Repositories.NotificationRepository;
import com.tekup.pfaapisb.Repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserEntityRepository userEntityRepository;

    public NotificationDTO createNewNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setMessage(notificationDTO.getMessage());
        notification.setDateCreation(LocalDate.now());
        notification.setRole(notificationDTO.getRole());

        notificationRepository.save(notification);

        NotificationDTO savedNotificationDTO = new NotificationDTO();
        savedNotificationDTO.setTitle(notification.getTitle());
        savedNotificationDTO.setMessage(notification.getMessage());
        savedNotificationDTO.setDateCreation(notification.getDateCreation());
        savedNotificationDTO.setRole(notification.getRole());

        return savedNotificationDTO;
    }

    public List<NotificationDTO> getAllNotificationsByRole(Authentication authentication) {
        String email = authentication.getName();
        UserEntity user = userEntityRepository.findByEmailOrId(email,999999999)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Notification> notifications =  notificationRepository.getNotificationsByRole(user.getRole());
        List<NotificationDTO> notificationsDTO = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setTitle(notification.getTitle());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setRole(notification.getRole());
            notificationDTO.setDateCreation(notification.getDateCreation());
            notificationsDTO.add(notificationDTO);
        }
        return notificationsDTO;

    }
}
