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

import co.edu.umanizales.helpdesku.model.status;
import co.edu.umanizales.helpdesku.service.statusservice;

@RestController
@RequestMapping({"/api/status"})
public class statuscontroller {

    private final statusservice statusService;

    public statuscontroller(statusservice statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public ResponseEntity<List<status>> getAll() {
        return ResponseEntity.ok(statusService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<status> getById(@PathVariable String id) {
        status found = statusService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<status> create(@RequestBody status request) {
        request.setId(null);
        status saved = statusService.saveStatus(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<status> update(@PathVariable String id, @RequestBody status request) {
        status existing = statusService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(statusService.saveStatus(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = statusService.deleteStatus(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
