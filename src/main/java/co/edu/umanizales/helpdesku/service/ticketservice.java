package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.Category;
import co.edu.umanizales.helpdesku.model.Priority;
import co.edu.umanizales.helpdesku.model.Status;
import co.edu.umanizales.helpdesku.model.Ticket;
import co.edu.umanizales.helpdesku.model.User;

@Service
public class TicketService extends CsvBaseService<Ticket> {

    private final UserService userService;
    private final StatusService statusService;
    private final CategoryService categoryService;
    private final PriorityService priorityService;

    public TicketService(UserService userService,
                         StatusService statusService,
                         CategoryService categoryService,
                         PriorityService priorityService) {
        super("tickets.csv");
        this.userService = userService;
        this.statusService = statusService;
        this.categoryService = categoryService;
        this.priorityService = priorityService;
    }

    @Override
    protected Ticket createEmpty() {
        return new Ticket();
    }

    @Override
    protected String[] headerRow() {
        Ticket sample = new Ticket();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Ticket> list() {
        List<Ticket> tickets = findAll();
        for (int index = 0; index < tickets.size(); index++) {
            hydrate(tickets.get(index));
        }
        return tickets;
    }

    public Ticket getById(String id) {
        return hydrate(findById(id));
    }

    public Ticket getDetailedCopyById(String id) {
        Ticket stored = findById(id);
        if (stored == null) {
            return null;
        }
        Ticket clone = copyOf(stored);
        return hydrate(clone);
    }

    public Ticket saveTicket(Ticket entity) {
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

    private void ensureActiveUser(User reference, java.util.function.Consumer<User> setter) {
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            setter.accept(null);
            return;
        }
        try {
            User active = userService.requireActiveUserById(reference.getId());
            setter.accept(active);
        } catch (BadRequestException exception) {
            throw new BadRequestException("Id usuario no existente, no activo o no valido");
        }
    }

    private void ensureStatusExists(Ticket entity) {
        Status reference = entity.getStatus();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de los estados existentes");
        }
        Status stored = statusService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de los estados existentes");
        }
        entity.setStatus(stored);
    }

    private void ensureCategoryExists(Ticket entity) {
        Category reference = entity.getCategory();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de las categorias existentes");
        }
        Category stored = categoryService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de las categorias existentes");
        }
        entity.setCategory(stored);
    }

    private void ensurePriorityExists(Ticket entity) {
        Priority reference = entity.getPriority();
        if (reference == null || reference.getId() == null || reference.getId().isBlank()) {
            throw new BadRequestException("Id no valido de las prioridades existentes");
        }
        Priority stored = priorityService.getById(reference.getId());
        if (stored == null) {
            throw new BadRequestException("Id no valido de las prioridades existentes");
        }
        entity.setPriority(stored);
    }

    private Ticket hydrate(Ticket entity) {
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

    private Ticket copyOf(Ticket source) {
        Ticket clone = new Ticket();
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
