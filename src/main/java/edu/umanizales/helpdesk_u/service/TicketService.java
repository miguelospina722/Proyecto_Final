package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.Comment;
import edu.umanizales.helpdesk_u.model.Priority;
import edu.umanizales.helpdesk_u.model.Ticket;
import edu.umanizales.helpdesk_u.model.TicketStatus;
import edu.umanizales.helpdesk_u.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    public void changeStatus(Ticket ticket, TicketStatus newStatus) {
        throw new UnsupportedOperationException("Implement CSV-backed status change using ticket relationships");
    }

    public void changePriority(Ticket ticket, Priority newPriority) {
        throw new UnsupportedOperationException("Implement CSV-backed priority change using ticket relationships");
    }

    public void reassignTicket(Ticket ticket, User newAssignee) {
        throw new UnsupportedOperationException("Implement CSV-backed reassignment using user entities");
    }

    public void addComment(Ticket ticket, Comment comment) {
        throw new UnsupportedOperationException("Implement CSV-backed comment addition using ticket relationships");
    }

    public LocalDateTime calculateDueDateBySla(Ticket ticket) {
        throw new UnsupportedOperationException("Implement SLA due-date calculation using configured policies");
    }
}
