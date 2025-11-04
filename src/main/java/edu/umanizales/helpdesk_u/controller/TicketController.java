package edu.umanizales.helpdesk_u.controller;

import edu.umanizales.helpdesk_u.model.Comment;
import edu.umanizales.helpdesk_u.model.Priority;
import edu.umanizales.helpdesk_u.model.Ticket;
import edu.umanizales.helpdesk_u.model.TicketStatus;
import edu.umanizales.helpdesk_u.model.User;
import edu.umanizales.helpdesk_u.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") String ticketId,
                                             @RequestBody ChangeStatusRequest request) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        ticketService.changeStatus(ticket, request.newStatus());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/priority")
    public ResponseEntity<Void> changePriority(@PathVariable("id") String ticketId,
                                               @RequestBody ChangePriorityRequest request) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        ticketService.changePriority(ticket, request.newPriority());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/reassign")
    public ResponseEntity<Void> reassign(@PathVariable("id") String ticketId,
                                          @RequestBody ReassignTicketRequest request) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        User newAssignee = User.builder().id(request.newAssigneeId()).build();
        ticketService.reassignTicket(ticket, newAssignee);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable("id") String ticketId,
                                           @RequestBody Comment comment) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        comment.setTicket(ticket);
        ticketService.addComment(ticket, comment);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/sla")
    public ResponseEntity<LocalDateTime> calculateDueDate(@PathVariable("id") String ticketId) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        LocalDateTime dueDate = ticketService.calculateDueDateBySla(ticket);
        return ResponseEntity.ok(dueDate);
    }

    public record ChangeStatusRequest(TicketStatus newStatus) {
    }

    public record ChangePriorityRequest(Priority newPriority) {
    }

    public record ReassignTicketRequest(String newAssigneeId) {
    }
}
