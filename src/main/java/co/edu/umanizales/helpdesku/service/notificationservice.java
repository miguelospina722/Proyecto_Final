package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.notification;

@Service
public class notificationservice extends csvbaseservice<notification> {

    public notificationservice() {
        super("notifications.csv");
    }

    @Override
    protected notification createEmpty() {
        return new notification();
    }

    @Override
    protected String[] headerRow() {
        notification sample = new notification();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<notification> list() {
        return findAll();
    }

    public notification getById(String id) {
        return findById(id);
    }

    public notification saveNotification(notification entity) {
        return save(entity);
    }

    public boolean deleteNotification(String id) {
        return delete(id);
    }
}
