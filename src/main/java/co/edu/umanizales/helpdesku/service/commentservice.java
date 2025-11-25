package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Comment;
import co.edu.umanizales.helpdesku.model.Ticket;
import co.edu.umanizales.helpdesku.model.User;

@Service
public class CommentService extends CsvBaseService<Comment> {

    private final UserService userService;
    private final TicketService ticketService;

    public CommentService(UserService userService, TicketService ticketService) {
        super("comments.csv");
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    protected Comment createEmpty() {
        return new Comment();
    }

    @Override
    protected String[] headerRow() {
        Comment sample = new Comment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Comment> list() {
        List<Comment> comments = findAll();
        for (int index = 0; index < comments.size(); index++) {
            hydrate(comments.get(index));
        }
        return comments;
    }

    public Comment getById(String id) {
        return hydrate(findById(id));
    }

    public Comment saveComment(Comment entity) {
        ensureTicketExists(entity);
        ensureActiveAuthor(entity);
        return save(entity);
    }

    public boolean deleteComment(String id) {
        return delete(id);
    }

    private void ensureActiveAuthor(Comment entity) {
        User author = entity.getAuthor();
        if (author == null || author.getId() == null || author.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setAuthor(userService.requireActiveUserById(author.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(Comment entity) {
        Ticket reference = entity.getTicket();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Ticket no encontrado");
        }
        Ticket stored = ticketService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Ticket no encontrado");
        }
        entity.setTicket(stored);
    }

    private Comment hydrate(Comment entity) {
        if (entity == null) {
            return null;
        }
        Ticket ticket = entity.getTicket();
        if (ticket != null && ticket.getId() != null && !ticket.getId().isBlank()) {
            Ticket storedTicket = ticketService.getById(ticket.getId());
            if (storedTicket != null) {
                entity.setTicket(storedTicket);
            }
        }
        User author = entity.getAuthor();
        if (author != null && author.getId() != null && !author.getId().isBlank()) {
            User storedUser = userService.getById(author.getId());
            if (storedUser != null) {
                entity.setAuthor(storedUser);
            }
        }
        return entity;
    }
}
