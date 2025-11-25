package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.Priority;

@Service
public class PriorityService extends CsvBaseService<Priority> {

    public PriorityService() {
        super("priorities.csv");
    }

    @Override
    protected Priority createEmpty() {
        return new Priority();
    }

    @Override
    protected String[] headerRow() {
        Priority sample = new Priority();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Priority> list() {
        return findAll();
    }

    public Priority getById(String id) {
        return findById(id);
    }

    public Priority savePriority(Priority entity) {
        return save(entity);
    }

    public boolean deletePriority(String id) {
        return delete(id);
    }
}
