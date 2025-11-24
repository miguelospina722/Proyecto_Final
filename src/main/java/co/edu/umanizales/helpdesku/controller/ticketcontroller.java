package co.edu.umanizales.helpdesku.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.service.ticketservice;

@RestController
@RequestMapping("/api/tickets")
public class ticketcontroller {

    private final ticketservice ticketService;

    public ticketcontroller(ticketservice ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<ticket>> getAll() {
        return ResponseEntity.ok(ticketService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ticket> getById(@PathVariable String id) {
        ticket found = ticketService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<ticket> create(@RequestBody ticket request) {
        request.setId(null);
        ticket saved = ticketService.saveTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ticket> update(@PathVariable String id, @RequestBody ticket request) {
        ticket existing = ticketService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        mergeMissingFields(request, existing);
        return ResponseEntity.ok(ticketService.saveTicket(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = ticketService.deleteTicket(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private static void mergeMissingFields(ticket target, ticket existing) {
        if (target.getTitle() == null) {
            target.setTitle(existing.getTitle());
        }
        if (target.getDescription() == null) {
            target.setDescription(existing.getDescription());
        }
        if (target.getStatus() == null || target.getStatus().getId() == null) {
            target.setStatus(existing.getStatus());
        }
        if (target.getCategory() == null || target.getCategory().getId() == null) {
            target.setCategory(existing.getCategory());
        }
        if (target.getPriority() == null || target.getPriority().getId() == null) {
            target.setPriority(existing.getPriority());
        }
        if (target.getRequester() == null || target.getRequester().getId() == null) {
            target.setRequester(existing.getRequester());
        }
        if (target.getAssignee() == null || target.getAssignee().getId() == null) {
            target.setAssignee(existing.getAssignee());
        }
        if (target.getDueDate() == null) {
            target.setDueDate(existing.getDueDate());
        }
    }
}
