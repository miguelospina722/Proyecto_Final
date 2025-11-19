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

import co.edu.umanizales.helpdesku.model.priority;
import co.edu.umanizales.helpdesku.service.priorityservice;

@RestController
@RequestMapping("/api/priorities")
public class prioritycontroller {

    private final priorityservice priorityService;

    public prioritycontroller(priorityservice priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    public ResponseEntity<List<priority>> getAll() {
        return ResponseEntity.ok(priorityService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<priority> getById(@PathVariable String id) {
        priority found = priorityService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<priority> create(@RequestBody priority request) {
        request.setId(null);
        priority saved = priorityService.savePriority(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<priority> update(@PathVariable String id, @RequestBody priority request) {
        priority existing = priorityService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(priorityService.savePriority(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = priorityService.deletePriority(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
