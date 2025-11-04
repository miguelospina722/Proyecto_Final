package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.Notification;
import edu.umanizales.helpdesk_u.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    public List<Notification> listNotificationsByUser(User user) {
        throw new UnsupportedOperationException("Implement CSV-backed notification listing using user relationships");
    }

    public Notification createNotification(Notification notification) {
        throw new UnsupportedOperationException("Implement CSV-backed notification creation");
    }

    public void markAsRead(Notification notification) {
        throw new UnsupportedOperationException("Implement CSV-backed notification read flag update using notification entity");
    }
}
