package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Assignment;
import co.edu.umanizales.helpdesku.model.Ticket;
import co.edu.umanizales.helpdesku.model.User;

@Service
public class AssignmentService extends CsvBaseService<Assignment> {

    private final UserService userService;
    private final TicketService ticketService;

    public AssignmentService(UserService userService, TicketService ticketService) {
        super("assignments.csv");
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    protected Assignment createEmpty() {
        return new Assignment();
    }

    @Override
    protected String[] headerRow() {
        Assignment sample = new Assignment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Assignment> list() {
        List<Assignment> assignments = findAll();
        for (int index = 0; index < assignments.size(); index++) {
            hydrate(assignments.get(index));
        }
        return assignments;
    }

    public Assignment getById(String id) {
        return hydrate(findById(id));
    }

    public Assignment saveAssignment(Assignment entity) {
        ensureTicketExists(entity);
        ensureActiveTechnician(entity);
        return save(entity);
    }

    public boolean deleteAssignment(String id) {
        return delete(id);
    }

    private void ensureActiveTechnician(Assignment entity) {
        User technician = entity.getTechnician();
        if (technician == null || technician.getId() == null || technician.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setTechnician(userService.requireActiveUserById(technician.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(Assignment entity) {
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

    private Assignment hydrate(Assignment entity) {
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
        User technician = entity.getTechnician();
        if (technician != null && technician.getId() != null && !technician.getId().isBlank()) {
            User storedUser = userService.getById(technician.getId());
            if (storedUser != null) {
                entity.setTechnician(storedUser);
            }
        }
        return entity;
    }
}
