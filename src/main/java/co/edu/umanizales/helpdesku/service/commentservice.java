package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.comment;
import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.model.user;

@Service
public class commentservice extends csvbaseservice<comment> {

    private final userservice userService;
    private final ticketservice ticketService;

    public commentservice(userservice userService, ticketservice ticketService) {
        super("comments.csv");
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    protected comment createEmpty() {
        return new comment();
    }

    @Override
    protected String[] headerRow() {
        comment sample = new comment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<comment> list() {
        List<comment> comments = findAll();
        for (int index = 0; index < comments.size(); index++) {
            hydrate(comments.get(index));
        }
        return comments;
    }

    public comment getById(String id) {
        return hydrate(findById(id));
    }

    public comment saveComment(comment entity) {
        ensureTicketExists(entity);
        ensureActiveAuthor(entity);
        return save(entity);
    }

    public boolean deleteComment(String id) {
        return delete(id);
    }

    private void ensureActiveAuthor(comment entity) {
        user author = entity.getAuthor();
        if (author == null || author.getId() == null || author.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setAuthor(userService.requireActiveUserById(author.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(comment entity) {
        ticket reference = entity.getTicket();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Ticket no encontrado");
        }
        ticket stored = ticketService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Ticket no encontrado");
        }
        entity.setTicket(stored);
    }

    private comment hydrate(comment entity) {
        if (entity == null) {
            return null;
        }
        ticket ticket = entity.getTicket();
        if (ticket != null && ticket.getId() != null && !ticket.getId().isBlank()) {
            ticket storedTicket = ticketService.getById(ticket.getId());
            if (storedTicket != null) {
                entity.setTicket(storedTicket);
            }
        }
        user author = entity.getAuthor();
        if (author != null && author.getId() != null && !author.getId().isBlank()) {
            user storedUser = userService.getById(author.getId());
            if (storedUser != null) {
                entity.setAuthor(storedUser);
            }
        }
        return entity;
    }
}
