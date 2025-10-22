package edu.umanizales.mesadeayuda_u.repository;

import edu.umanizales.mesadeayuda_u.model.ExportableCSV;
import edu.umanizales.mesadeayuda_u.model.Identificable;

import java.util.List;
import java.util.Optional;

public interface CsvRepository<T extends ExportableCSV & Identificable> {

    List<T> findAll();

    Optional<T> findById(String id);

    T save(T entity);

    void deleteById(String id);

    void deleteAll();
}
