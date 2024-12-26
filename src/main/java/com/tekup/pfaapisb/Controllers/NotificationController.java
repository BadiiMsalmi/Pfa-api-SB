package com.tekup.pfaapisb.Controllers;


import com.tekup.pfaapisb.DTO.NotificationDTO;
import com.tekup.pfaapisb.Enum.Role;
import com.tekup.pfaapisb.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/createNew")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(notificationService.createNewNotification(notificationDTO));
    }

    @GetMapping("/getNotifs")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsByRole(Authentication authentication) {
        return ResponseEntity.ok(notificationService.getAllNotificationsByRole(authentication));
    }


}
