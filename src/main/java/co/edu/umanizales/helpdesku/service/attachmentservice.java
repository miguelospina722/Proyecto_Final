package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.attachment;
import co.edu.umanizales.helpdesku.model.ticket;

@Service
public class attachmentservice extends csvbaseservice<attachment> {

    private final ticketservice ticketService;

    public attachmentservice(ticketservice ticketService) {
        super("attachments.csv");
        this.ticketService = ticketService;
    }

    @Override
    protected attachment createEmpty() {
        return new attachment();
    }

    @Override
    protected String[] headerRow() {
        attachment sample = new attachment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<attachment> list() {
        List<attachment> attachments = findAll();
        for (int index = 0; index < attachments.size(); index++) {
            hydrate(attachments.get(index));
        }
        return attachments;
    }

    public attachment getById(String id) {
        return hydrate(findById(id));
    }

    public attachment saveAttachment(attachment entity) {
        ensureTicketExists(entity);
        return save(entity);
    }

    public boolean deleteAttachment(String id) {
        return delete(id);
    }

    private void ensureTicketExists(attachment entity) {
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

    private attachment hydrate(attachment entity) {
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
}
