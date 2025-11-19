package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.ticket;

@Service
public class ticketservice extends csvbaseservice<ticket> {

    public ticketservice() {
        super("tickets.csv");
    }

    @Override
    protected ticket createEmpty() {
        return new ticket();
    }

    @Override
    protected String[] headerRow() {
        ticket sample = new ticket();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<ticket> list() {
        return findAll();
    }

    public ticket getById(String id) {
        return findById(id);
    }

    public ticket saveTicket(ticket entity) {
        return save(entity);
    }

    public boolean deleteTicket(String id) {
        return delete(id);
    }
}
