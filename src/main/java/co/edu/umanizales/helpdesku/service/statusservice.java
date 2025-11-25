package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.Status;

@Service
public class StatusService extends CsvBaseService<Status> {

    public StatusService() {
        super("statuses.csv");
    }

    @Override
    protected Status createEmpty() {
        return new Status();
    }

    @Override
    protected String[] headerRow() {
        Status sample = new Status();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Status> list() {
        return findAll();
    }

    public Status getById(String id) {
        return findById(id);
    }

    public Status saveStatus(Status entity) {
        return save(entity);
    }

    public boolean deleteStatus(String id) {
        return delete(id);
    }
}
