package co.edu.umanizales.helpdesku.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";
    private static final String USER_ERROR_MESSAGE = "Id usuario no existente, no activo o no valido";

    private Ticket ticket;
    private User author;
    private String content;
    private LocalDateTime commentedAt;

    @Override
    public String toCsv() {
        validateTicket();
        validateAuthor();
        StringBuilder builder = new StringBuilder(buildBaseCsv());
        appendEntityId(builder, ticket);
        appendEntityId(builder, author);
        appendString(builder, content);
        appendNullable(builder, commentedAt);
        return builder.toString();
    }

    private void validateTicket() {
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new BadRequestException(TICKET_ERROR_MESSAGE);
        }
    }

    private void validateAuthor() {
        if (author == null || author.getId() == null || author.getId().isBlank()) {
            throw new BadRequestException(USER_ERROR_MESSAGE);
        }
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        ticket = parseReference(data, 3, Ticket::new);
        author = parseReference(data, 4, User::new);
        content = valueAt(data, 5);
        commentedAt = parseDateTime(valueAt(data, 6));
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "author_id", "content", "commented_at");
    }
}
