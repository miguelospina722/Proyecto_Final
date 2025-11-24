package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.assignment;
import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.model.user;

@Service
public class assignmentservice extends csvbaseservice<assignment> {

    private final userservice userService;
    private final ticketservice ticketService;

    public assignmentservice(userservice userService, ticketservice ticketService) {
        super("assignments.csv");
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    protected assignment createEmpty() {
        return new assignment();
    }

    @Override
    protected String[] headerRow() {
        assignment sample = new assignment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<assignment> list() {
        List<assignment> assignments = findAll();
        for (int index = 0; index < assignments.size(); index++) {
            hydrate(assignments.get(index));
        }
        return assignments;
    }

    public assignment getById(String id) {
        return hydrate(findById(id));
    }

    public assignment saveAssignment(assignment entity) {
        ensureTicketExists(entity);
        ensureActiveTechnician(entity);
        return save(entity);
    }

    public boolean deleteAssignment(String id) {
        return delete(id);
    }

    private void ensureActiveTechnician(assignment entity) {
        user technician = entity.getTechnician();
        if (technician == null || technician.getId() == null || technician.getId().isBlank()) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
        try {
            entity.setTechnician(userService.requireActiveUserById(technician.getId()));
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureTicketExists(assignment entity) {
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

    private assignment hydrate(assignment entity) {
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
        user technician = entity.getTechnician();
        if (technician != null && technician.getId() != null && !technician.getId().isBlank()) {
            user storedUser = userService.getById(technician.getId());
            if (storedUser != null) {
                entity.setTechnician(storedUser);
            }
        }
        return entity;
    }
}
