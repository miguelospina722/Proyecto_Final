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
public class notification extends baseentity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";
    private static final String USER_ERROR_MESSAGE = "Id usuario no existente, no activo o no valido";

    private ticket ticket;
    private user recipient;
    private String message;
    private LocalDateTime sentAt;
    private boolean read;

    @Override
    public String toCsv() {
        validateTicket();
        validateRecipient();
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(idOrEmpty(ticket));
        builder.append(",");
        builder.append(idOrEmpty(recipient));
        builder.append(",");
        builder.append(message == null ? "" : message);
        builder.append(",");
        if (sentAt != null) {
            builder.append(sentAt);
        }
        builder.append(",");
        builder.append(read);
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
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            ticket = referenceFromId(data[3], ticket::new);
        }
        if (data.length > 4) {
            recipient = referenceFromId(data[4], user::new);
        }
        if (data.length > 5) {
            message = data[5];
        }
        if (data.length > 6 && data[6] != null && !data[6].isEmpty()) {
            sentAt = LocalDateTime.parse(data[6]);
        }
        if (data.length > 7 && data[7] != null && !data[7].isEmpty()) {
            read = Boolean.parseBoolean(data[7]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "recipient_id", "message", "sent_at", "read");
    }
}
