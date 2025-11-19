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

import co.edu.umanizales.helpdesku.model.attachment;
import co.edu.umanizales.helpdesku.service.attachmentservice;

@RestController
@RequestMapping("/api/attachments")
public class attachmentcontroller {

    private final attachmentservice attachmentService;

    public attachmentcontroller(attachmentservice attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public ResponseEntity<List<attachment>> getAll() {
        return ResponseEntity.ok(attachmentService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<attachment> getById(@PathVariable String id) {
        attachment found = attachmentService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<attachment> create(@RequestBody attachment request) {
        request.setId(null);
        attachment saved = attachmentService.saveAttachment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<attachment> update(@PathVariable String id, @RequestBody attachment request) {
        attachment existing = attachmentService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(attachmentService.saveAttachment(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = attachmentService.deleteAttachment(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
