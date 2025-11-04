package edu.umanizales.helpdesk_u.controller;

import edu.umanizales.helpdesk_u.model.Notification;
import edu.umanizales.helpdesk_u.model.User;
import edu.umanizales.helpdesk_u.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> listByUser(@PathVariable("userId") String userId) {
        User user = User.builder().id(userId).build();
        return ResponseEntity.ok(notificationService.listNotificationsByUser(user));
    }

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable("id") String notificationId) {
        Notification notification = Notification.builder().id(notificationId).build();
        notificationService.markAsRead(notification);
        return ResponseEntity.accepted().build();
    }
}
