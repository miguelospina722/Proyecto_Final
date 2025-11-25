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
public class Notification extends BaseEntity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";
    private static final String USER_ERROR_MESSAGE = "Id usuario no existente, no activo o no valido";

    private Ticket ticket;
    private User recipient;
    private String message;
    private LocalDateTime sentAt;
    private boolean read;

    @Override
    public String toCsv() {
        validateTicket();
        validateRecipient();
        StringBuilder builder = new StringBuilder(buildBaseCsv());
        appendEntityId(builder, ticket);
        appendEntityId(builder, recipient);
        appendString(builder, message);
        appendNullable(builder, sentAt);
        appendString(builder, Boolean.toString(read));
        return builder.toString();
    }

    private void validateTicket() {
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new BadRequestException(TICKET_ERROR_MESSAGE);
        }
    }

    private void validateRecipient() {
        if (recipient == null || recipient.getId() == null || recipient.getId().isBlank()) {
            throw new BadRequestException(USER_ERROR_MESSAGE);
        }
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        ticket = parseReference(data, 3, Ticket::new);
        recipient = parseReference(data, 4, User::new);
        message = valueAt(data, 5);
        sentAt = parseDateTime(valueAt(data, 6));
        read = parseBoolean(valueAt(data, 7));
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "recipient_id", "message", "sent_at", "read");
    }
}
