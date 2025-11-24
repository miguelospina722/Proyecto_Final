package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.notification;
import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.model.user;

@Service
public class notificationservice extends csvbaseservice<notification> {

    private final userservice userService;
    private final ticketservice ticketService;

    public notificationservice(userservice userService, ticketservice ticketService) {
        super("notifications.csv");
        this.userService = userService;
        this.ticketService = ticketService;
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
        List<notification> notifications = findAll();
        for (int index = 0; index < notifications.size(); index++) {
            hydrate(notifications.get(index));
        }
        return notifications;
    }

    public notification getById(String id) {
        return hydrate(findById(id));
    }

    public notification saveNotification(notification entity) {
        ensureTicketExists(entity);
        ensureActiveRecipient(entity);
        return save(entity);
    }

    public boolean deleteNotification(String id) {
        return delete(id);
    }

    private void ensureActiveRecipient(notification entity) {
        user recipient = entity.getRecipient();
        if (recipient == null || recipient.getId() == null || recipient.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setRecipient(userService.requireActiveUserById(recipient.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(notification entity) {
        ticket reference = entity.getTicket();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Ticket no encontrado");
        }
        ticket stored = ticketService.getDetailedCopyById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Ticket no encontrado");
        }
        entity.setTicket(stored);
    }

    private notification hydrate(notification entity) {
        if (entity == null) {
            return null;
        }
        ticket ticket = entity.getTicket();
        if (ticket != null && ticket.getId() != null && !ticket.getId().isBlank()) {
            ticket storedTicket = ticketService.getDetailedCopyById(ticket.getId());
            if (storedTicket != null) {
                entity.setTicket(storedTicket);
            }
        }
        user recipient = entity.getRecipient();
        if (recipient != null && recipient.getId() != null && !recipient.getId().isBlank()) {
            user storedUser = userService.getById(recipient.getId());
            if (storedUser != null) {
                entity.setRecipient(storedUser);
            }
        }
        return entity;
    }
}
