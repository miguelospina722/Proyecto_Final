package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Notification;
import co.edu.umanizales.helpdesku.model.Ticket;
import co.edu.umanizales.helpdesku.model.User;

@Service
public class NotificationService extends CsvBaseService<Notification> {

    private final UserService userService;
    private final TicketService ticketService;

    public NotificationService(UserService userService, TicketService ticketService) {
        super("notifications.csv");
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    protected Notification createEmpty() {
        return new Notification();
    }

    @Override
    protected String[] headerRow() {
        Notification sample = new Notification();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Notification> list() {
        List<Notification> notifications = findAll();
        for (int index = 0; index < notifications.size(); index++) {
            hydrate(notifications.get(index));
        }
        return notifications;
    }

    public Notification getById(String id) {
        return hydrate(findById(id));
    }

    public Notification saveNotification(Notification entity) {
        ensureTicketExists(entity);
        ensureActiveRecipient(entity);
        return save(entity);
    }

    public boolean deleteNotification(String id) {
        return delete(id);
    }

    private void ensureActiveRecipient(Notification entity) {
        User recipient = entity.getRecipient();
        if (recipient == null || recipient.getId() == null || recipient.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setRecipient(userService.requireActiveUserById(recipient.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(Notification entity) {
        Ticket reference = entity.getTicket();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Ticket no encontrado");
        }
        Ticket stored = ticketService.getDetailedCopyById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Ticket no encontrado");
        }
        entity.setTicket(stored);
    }

    private Notification hydrate(Notification entity) {
        if (entity == null) {
            return null;
        }
        Ticket ticket = entity.getTicket();
        if (ticket != null && ticket.getId() != null && !ticket.getId().isBlank()) {
            Ticket storedTicket = ticketService.getDetailedCopyById(ticket.getId());
            if (storedTicket != null) {
                entity.setTicket(storedTicket);
            }
        }
        User recipient = entity.getRecipient();
        if (recipient != null && recipient.getId() != null && !recipient.getId().isBlank()) {
            User storedUser = userService.getById(recipient.getId());
            if (storedUser != null) {
                entity.setRecipient(storedUser);
            }
        }
        return entity;
    }
}
