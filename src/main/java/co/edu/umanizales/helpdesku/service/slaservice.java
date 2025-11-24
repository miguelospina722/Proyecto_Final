package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.sla;
import co.edu.umanizales.helpdesku.model.ticket;

@Service
public class slaservice extends csvbaseservice<sla> {

    private final ticketservice ticketService;

    public slaservice(ticketservice ticketService) {
        super("sla.csv");
        this.ticketService = ticketService;
    }

    @Override
    protected sla createEmpty() {
        return new sla();
    }

    @Override
    protected String[] headerRow() {
        sla sample = new sla();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<sla> list() {
        List<sla> slas = findAll();
        for (int index = 0; index < slas.size(); index++) {
            hydrate(slas.get(index));
        }
        return slas;
    }

    public sla getById(String id) {
        return hydrate(findById(id));
    }

    public sla saveSla(sla entity) {
        ensureTicketExists(entity);
        return save(entity);
    }

    public boolean deleteSla(String id) {
        return delete(id);
    }

    private sla hydrate(sla entity) {
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
        return entity;
    }

    private void ensureTicketExists(sla entity) {
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
}
