package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Sla;
import co.edu.umanizales.helpdesku.model.Ticket;

@Service
public class SlaService extends CsvBaseService<Sla> {

    private final TicketService ticketService;

    public SlaService(TicketService ticketService) {
        super("sla.csv");
        this.ticketService = ticketService;
    }

    @Override
    protected Sla createEmpty() {
        return new Sla();
    }

    @Override
    protected String[] headerRow() {
        Sla sample = new Sla();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Sla> list() {
        List<Sla> slas = findAll();
        for (int index = 0; index < slas.size(); index++) {
            hydrate(slas.get(index));
        }
        return slas;
    }

    public Sla getById(String id) {
        return hydrate(findById(id));
    }

    public Sla saveSla(Sla entity) {
        ensureTicketExists(entity);
        return save(entity);
    }

    public boolean deleteSla(String id) {
        return delete(id);
    }

    private Sla hydrate(Sla entity) {
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
        return entity;
    }

    private void ensureTicketExists(Sla entity) {
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
}
