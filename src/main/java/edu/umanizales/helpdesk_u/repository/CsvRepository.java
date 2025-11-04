package edu.umanizales.helpdesk_u.repository;

import edu.umanizales.helpdesk_u.model.CsvExportable;
import edu.umanizales.helpdesk_u.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface CsvRepository<T extends CsvExportable & Identifiable> {

    List<T> findAll();

    Optional<T> findById(String id);

    T save(T entity);

    void deleteById(String id);

    void deleteAll();
}
