package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.category;
import co.edu.umanizales.helpdesku.model.priority;
import co.edu.umanizales.helpdesku.model.status;
import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.model.user;

@Service
public class ticketservice extends csvbaseservice<ticket> {

    private final userservice userService;
    private final statusservice statusService;
    private final categoryservice categoryService;
    private final priorityservice priorityService;

    public ticketservice(userservice userService,
                         statusservice statusService,
                         categoryservice categoryService,
                         priorityservice priorityService) {
        super("tickets.csv");
        this.userService = userService;
        this.statusService = statusService;
        this.categoryService = categoryService;
        this.priorityService = priorityService;
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
        List<ticket> tickets = findAll();
        for (int index = 0; index < tickets.size(); index++) {
            hydrate(tickets.get(index));
        }
        return tickets;
    }

    public ticket getById(String id) {
        return hydrate(findById(id));
    }

    public ticket getDetailedCopyById(String id) {
        ticket stored = findById(id);
        if (stored == null) {
            return null;
        }
        ticket clone = copyOf(stored);
        return hydrate(clone);
    }

    public ticket saveTicket(ticket entity) {
        ensureStatusExists(entity);
        ensureCategoryExists(entity);
        ensurePriorityExists(entity);
        ensureActiveUser(entity.getRequester(), entity::setRequester);
        ensureActiveUser(entity.getAssignee(), entity::setAssignee);
        return save(entity);
    }

    public boolean deleteTicket(String id) {
        return delete(id);
    }

    private void ensureActiveUser(user reference, java.util.function.Consumer<user> setter) {
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            setter.accept(null);
            return;
        }
        try {
            user active = userService.requireActiveUserById(reference.getId());
            setter.accept(active);
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureStatusExists(ticket entity) {
        status reference = entity.getStatus();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de los estados existentes");
        }
        status stored = statusService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de los estados existentes");
        }
        entity.setStatus(stored);
    }

    private void ensureCategoryExists(ticket entity) {
        category reference = entity.getCategory();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de las categorias existentes");
        }
        category stored = categoryService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de las categorias existentes");
        }
        entity.setCategory(stored);
    }

    private void ensurePriorityExists(ticket entity) {
        priority reference = entity.getPriority();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de las prioridades existentes");
        }
        priority stored = priorityService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de las prioridades existentes");
        }
        entity.setPriority(stored);
    }

    private ticket hydrate(ticket entity) {
        if (entity == null) {
            return null;
        }
        ensureStatusExists(entity);
        ensureCategoryExists(entity);
        ensurePriorityExists(entity);
        ensureActiveUser(entity.getRequester(), entity::setRequester);
        ensureActiveUser(entity.getAssignee(), entity::setAssignee);
        return entity;
    }

    private ticket copyOf(ticket source) {
        ticket clone = new ticket();
        clone.setId(source.getId());
        clone.setCreatedAt(source.getCreatedAt());
        clone.setUpdatedAt(source.getUpdatedAt());
        clone.setTitle(source.getTitle());
        clone.setDescription(source.getDescription());
        clone.setStatus(source.getStatus());
        clone.setCategory(source.getCategory());
        clone.setPriority(source.getPriority());
        clone.setRequester(source.getRequester());
        clone.setAssignee(source.getAssignee());
        clone.setDueDate(source.getDueDate());
        return clone;
    }
}
