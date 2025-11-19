package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.status;

@Service
public class statusservice extends csvbaseservice<status> {

    public statusservice() {
        super("statuses.csv");
    }

    @Override
    protected status createEmpty() {
        return new status();
    }

    @Override
    protected String[] headerRow() {
        status sample = new status();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<status> list() {
        return findAll();
    }

    public status getById(String id) {
        return findById(id);
    }

    public status saveStatus(status entity) {
        return save(entity);
    }

    public boolean deleteStatus(String id) {
        return delete(id);
    }
}
