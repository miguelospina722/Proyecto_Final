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

import co.edu.umanizales.helpdesku.model.assignment;
import co.edu.umanizales.helpdesku.service.assignmentservice;

@RestController
@RequestMapping("/api/assignments")
public class assignmentcontroller {

    private final assignmentservice assignmentService;

    public assignmentcontroller(assignmentservice assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<assignment>> getAll() {
        return ResponseEntity.ok(assignmentService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<assignment> getById(@PathVariable String id) {
        assignment found = assignmentService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<assignment> create(@RequestBody assignment request) {
        request.setId(null);
        assignment saved = assignmentService.saveAssignment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<assignment> update(@PathVariable String id, @RequestBody assignment request) {
        assignment existing = assignmentService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(assignmentService.saveAssignment(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = assignmentService.deleteAssignment(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
