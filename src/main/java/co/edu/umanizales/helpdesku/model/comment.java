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
public class comment extends baseentity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";
    private static final String USER_ERROR_MESSAGE = "Id usuario no existente, no activo o no valido";

    private ticket ticket;
    private user author;
    private String content;
    private LocalDateTime commentedAt;

    @Override
    public String toCsv() {
        validateTicket();
        validateAuthor();
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(idOrEmpty(ticket));
        builder.append(",");
        builder.append(idOrEmpty(author));
        builder.append(",");
        builder.append(content == null ? "" : content);
        builder.append(",");
        if (commentedAt != null) {
            builder.append(commentedAt);
        }
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
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            ticket = referenceFromId(data[3], ticket::new);
        }
        if (data.length > 4) {
            author = referenceFromId(data[4], user::new);
        }
        if (data.length > 5) {
            content = data[5];
        }
        if (data.length > 6 && data[6] != null && !data[6].isEmpty()) {
            commentedAt = LocalDateTime.parse(data[6]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "author_id", "content", "commented_at");
    }
}
