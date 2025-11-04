package edu.umanizales.helpdesk_u.controller;

import edu.umanizales.helpdesk_u.model.Assignment;
import edu.umanizales.helpdesk_u.model.Ticket;
import edu.umanizales.helpdesk_u.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Assignment>> listByTicket(@PathVariable("ticketId") String ticketId) {
        Ticket ticket = Ticket.builder().id(ticketId).build();
        return ResponseEntity.ok(assignmentService.listAssignmentsByTicket(ticket));
    }

    @PostMapping
    public ResponseEntity<Assignment> register(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.registerAssignment(assignment));
    }
}
