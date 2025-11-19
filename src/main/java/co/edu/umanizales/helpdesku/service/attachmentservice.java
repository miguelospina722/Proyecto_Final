package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.attachment;

@Service
public class attachmentservice extends csvbaseservice<attachment> {

    public attachmentservice() {
        super("attachments.csv");
    }

    @Override
    protected attachment createEmpty() {
        return new attachment();
    }

    @Override
    protected String[] headerRow() {
        attachment sample = new attachment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<attachment> list() {
        return findAll();
    }

    public attachment getById(String id) {
        return findById(id);
    }

    public attachment saveAttachment(attachment entity) {
        return save(entity);
    }

    public boolean deleteAttachment(String id) {
        return delete(id);
    }
}
