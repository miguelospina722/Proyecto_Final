package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.sla;

@Service
public class slaservice extends csvbaseservice<sla> {

    public slaservice() {
        super("sla.csv");
    }

    @Override
    protected sla createEmpty() {
        return new sla();
    }

    @Override
    protected String[] headerRow() {
        sla sample = new sla();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<sla> list() {
        return findAll();
    }

    public sla getById(String id) {
        return findById(id);
    }

    public sla saveSla(sla entity) {
        return save(entity);
    }

    public boolean deleteSla(String id) {
        return delete(id);
    }
}
