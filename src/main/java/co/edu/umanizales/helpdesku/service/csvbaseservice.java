package co.edu.umanizales.helpdesku.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.umanizales.helpdesku.model.BaseEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public abstract class CsvBaseService<T extends BaseEntity> implements ApplicationEventPublisherAware {

    private final List<T> items;
    private final Path filePath;
    private ApplicationEventPublisher applicationEventPublisher;

    protected CsvBaseService(String fileName) {
        items = new ArrayList<>();
        filePath = Paths.get("data", fileName);
        ensureFileExists();
        loadFromFile();
    }

    protected abstract T createEmpty();

    protected abstract String[] headerRow();

    public List<T> findAll() {
        List<T> copy = new ArrayList<>();
        for (int index = 0; index < items.size(); index++) {
            copy.add(items.get(index));
        }
        return copy;
    }

    public T findById(String id) {
        if (id == null) {
            return null;
        }
        for (int index = 0; index < items.size(); index++) {
            T current = items.get(index);
            if (id.equals(current.getId())) {
                return current;
            }
        }
        return null;
    }

    public T save(T entity) {
        if (entity.getId() == null || entity.getId().isEmpty()) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(entity.getCreatedAt());
            items.add(entity);
        } else {
            boolean updated = false;
            for (int index = 0; index < items.size(); index++) {
                T current = items.get(index);
                if (entity.getId().equals(current.getId())) {
                    entity.setCreatedAt(current.getCreatedAt());
                    entity.setUpdatedAt(LocalDateTime.now());
                    items.set(index, entity);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                entity.setCreatedAt(LocalDateTime.now());
                entity.setUpdatedAt(entity.getCreatedAt());
                items.add(entity);
            }
        }
        persist();
        return entity;
    }

    public boolean delete(String id) {
        if (id == null) {
            return false;
        }
        for (int index = 0; index < items.size(); index++) {
            T current = items.get(index);
            if (id.equals(current.getId())) {
                items.remove(index);
                persist();
                return true;
            }
        }
        return false;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private void ensureFileExists() {
        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                writeHeaders();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to initialize storage for " + filePath, exception);
        }
    }

    private void writeHeaders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            String[] headers = headerRow();
            for (int index = 0; index < headers.length; index++) {
                writer.write(headers[index]);
                if (index < headers.length - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write header for " + filePath, exception);
        }
    }

    private void loadFromFile() {
        File file = filePath.toFile();
        if (!file.exists()) {
            ensureFileExists();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            boolean headerSkipped = false;
            String line = reader.readLine();
            while (line != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                } else {
                    if (line.trim().length() > 0) {
                        T entity = createEmpty();
                        entity.fromCsv(line);
                        items.add(entity);
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load data from " + filePath, exception);
        }
    }

    private void persist() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            String[] headers = headerRow();
            for (int index = 0; index < headers.length; index++) {
                writer.write(headers[index]);
                if (index < headers.length - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();
            for (int index = 0; index < items.size(); index++) {
                T entity = items.get(index);
                writer.write(entity.toCsv());
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to persist data to " + filePath, exception);
        }
        notifyCatalogChanged();
    }

    private void notifyCatalogChanged() {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(new EndpointCatalogChangedEvent(this));
        }
    }
}
