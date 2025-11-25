package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Attachment;
import co.edu.umanizales.helpdesku.model.Ticket;

@Service
public class AttachmentService extends CsvBaseService<Attachment> {

    private final TicketService ticketService;

    public AttachmentService(TicketService ticketService) {
        super("attachments.csv");
        this.ticketService = ticketService;
    }

    @Override
    protected Attachment createEmpty() {
        return new Attachment();
    }

    @Override
    protected String[] headerRow() {
        Attachment sample = new Attachment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Attachment> list() {
        List<Attachment> attachments = findAll();
        for (int index = 0; index < attachments.size(); index++) {
            hydrate(attachments.get(index));
        }
        return attachments;
    }

    public Attachment getById(String id) {
        return hydrate(findById(id));
    }

    public Attachment saveAttachment(Attachment entity) {
        ensureTicketExists(entity);
        return save(entity);
    }

    public boolean deleteAttachment(String id) {
        return delete(id);
    }

    private void ensureTicketExists(Attachment entity) {
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

    private Attachment hydrate(Attachment entity) {
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
}
