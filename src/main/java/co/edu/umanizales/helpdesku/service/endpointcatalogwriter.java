package co.edu.umanizales.helpdesku.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import co.edu.umanizales.helpdesku.model.assignment;
import co.edu.umanizales.helpdesku.model.category;
import co.edu.umanizales.helpdesku.model.priority;
import co.edu.umanizales.helpdesku.model.status;
import co.edu.umanizales.helpdesku.model.ticket;
import co.edu.umanizales.helpdesku.model.user;

import jakarta.annotation.PostConstruct;

@Component
public class endpointcatalogwriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(endpointcatalogwriter.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final userservice userService;
    private final categoryservice categoryService;
    private final priorityservice priorityService;
    private final statusservice statusService;
    private final ticketservice ticketService;
    private final assignmentservice assignmentService;

    private final Path targetPath = Paths.get("documents", "endpoint-ids.txt");

    public endpointcatalogwriter(userservice userService,
                                 categoryservice categoryService,
                                 priorityservice priorityService,
                                 statusservice statusService,
                                 ticketservice ticketService,
                                 assignmentservice assignmentService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.priorityService = priorityService;
        this.statusService = statusService;
        this.ticketService = ticketService;
        this.assignmentService = assignmentService;
    }

    @PostConstruct
    public void initializeCatalog() {
        regenerateCatalog();
    }

    @EventListener
    public void handleCatalogChanged(EndpointCatalogChangedEvent event) {
        regenerateCatalog();
    }

    private synchronized void regenerateCatalog() {
        try {
            Files.createDirectories(targetPath.getParent());
            Files.writeString(targetPath, buildContent(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            LOGGER.error("Unable to update endpoint IDs catalog", exception);
        }
    }

    private String buildContent() {
        StringBuilder builder = new StringBuilder();
        appendUsers(builder, userService.list());
        appendCategories(builder, categoryService.list());
        appendPriorities(builder, priorityService.list());
        appendStatuses(builder, statusService.list());
        appendTickets(builder, ticketService.list());
        appendAssignments(builder, assignmentService.list());
        return builder.toString();
    }

    private void appendUsers(StringBuilder builder, List<user> users) {
        builder.append("Usuarios:").append(System.lineSeparator());
        for (user current : users) {
            String description = String.format("%s (username: %s, rol %s%s)",
                    nullToDefault(current.getFullName(), "Sin nombre"),
                    nullToDefault(current.getUsername(), "Sin username"),
                    current.getRole() == null ? "SIN_ROL" : current.getRole().name(),
                    current.isActive() ? "" : ", inactivo");
            builder.append(formatLine(current.getId(), description)).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private void appendCategories(StringBuilder builder, List<category> categories) {
        builder.append("Categorías:").append(System.lineSeparator());
        for (category current : categories) {
            String description = String.format("%s (%s)",
                    current.getName() == null ? "SIN_CATEGORIA" : current.getName().name(),
                    nullToDefault(current.getDescription(), "Sin descripción"));
            builder.append(formatLine(current.getId(), description)).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private void appendPriorities(StringBuilder builder, List<priority> priorities) {
        builder.append("Prioridades:").append(System.lineSeparator());
        for (priority current : priorities) {
            String description = String.format("%s (%s, respuesta %d min, resolución %d min)",
                    nullToDefault(current.getName(), "Sin nombre"),
                    current.getLevel() == null ? "SIN_NIVEL" : current.getLevel().name(),
                    current.getResponseMinutes(),
                    current.getResolveMinutes());
            builder.append(formatLine(current.getId(), description)).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private void appendStatuses(StringBuilder builder, List<status> statuses) {
        builder.append("Estados:").append(System.lineSeparator());
        for (status current : statuses) {
            String description = String.format("%s (%s)",
                    current.getCode() == null ? "SIN_ESTADO" : current.getCode().name(),
                    nullToDefault(current.getDescription(), "Sin descripción"));
            builder.append(formatLine(current.getId(), description)).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private void appendTickets(StringBuilder builder, List<ticket> tickets) {
        builder.append("Tickets:").append(System.lineSeparator());
        for (ticket current : tickets) {
            String requesterName = current.getRequester() == null ? "Sin solicitante"
                    : nullToDefault(current.getRequester().getFullName(), current.getRequester().getUsername());
            String description = String.format("%s (solicitante %s)",
                    nullToDefault(current.getTitle(), "Sin título"),
                    nullToDefault(requesterName, "Sin solicitante"));
            builder.append(formatLine(current.getId(), description)).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private void appendAssignments(StringBuilder builder, List<assignment> assignments) {
        builder.append("Asignaciones:").append(System.lineSeparator());
        for (assignment current : assignments) {
            String ticketTitle = current.getTicket() == null ? "Sin ticket"
                    : nullToDefault(current.getTicket().getTitle(), current.getTicket().getId());
            String technicianName = current.getTechnician() == null ? "Sin técnico"
                    : nullToDefault(current.getTechnician().getFullName(), current.getTechnician().getUsername());
            StringBuilder description = new StringBuilder();
            description.append(String.format("Ticket \"%s\" asignado a %s", ticketTitle, technicianName));
            if (current.getAssignedAt() != null) {
                description.append(" (asignado ")
                        .append(DATE_TIME_FORMATTER.format(current.getAssignedAt()))
                        .append(")");
            }
            if (current.getNotes() != null && !current.getNotes().isBlank()) {
                description.append(" (nota: ").append(current.getNotes()).append(")");
            }
            builder.append(formatLine(current.getId(), description.toString())).append(System.lineSeparator());
        }
        builder.append(System.lineSeparator());
    }

    private String formatLine(String id, String description) {
        return String.format("%s - %s", nullToDefault(id, "SIN_ID"), description);
    }

    private String nullToDefault(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
