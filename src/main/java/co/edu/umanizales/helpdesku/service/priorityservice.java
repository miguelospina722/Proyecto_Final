package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.priority;

@Service
public class priorityservice extends csvbaseservice<priority> {

    public priorityservice() {
        super("priorities.csv");
    }

    @Override
    protected priority createEmpty() {
        return new priority();
    }

    @Override
    protected String[] headerRow() {
        priority sample = new priority();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<priority> list() {
        return findAll();
    }

    public priority getById(String id) {
        return findById(id);
    }

    public priority savePriority(priority entity) {
        return save(entity);
    }

    public boolean deletePriority(String id) {
        return delete(id);
    }
}
