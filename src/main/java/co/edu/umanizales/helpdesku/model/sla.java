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
public class sla extends baseentity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";

    private ticket ticket;
    private LocalDateTime responseDeadline;
    private LocalDateTime resolutionDeadline;
    private boolean breached;

    @Override
    public String toCsv() {
        validateTicket();
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(idOrEmpty(ticket));
        builder.append(",");
        if (responseDeadline != null) {
            builder.append(responseDeadline);
        }
        builder.append(",");
        if (resolutionDeadline != null) {
            builder.append(resolutionDeadline);
        }
        builder.append(",");
        builder.append(breached);
        return builder.toString();
    }

    private void validateTicket() {
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new BadRequestException(TICKET_ERROR_MESSAGE);
        }
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            ticket = referenceFromId(data[3], ticket::new);
        }
        if (data.length > 4 && data[4] != null && !data[4].isEmpty()) {
            responseDeadline = LocalDateTime.parse(data[4]);
        }
        if (data.length > 5 && data[5] != null && !data[5].isEmpty()) {
            resolutionDeadline = LocalDateTime.parse(data[5]);
        }
        if (data.length > 6 && data[6] != null && !data[6].isEmpty()) {
            breached = Boolean.parseBoolean(data[6]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "response_deadline", "resolution_deadline", "breached");
    }
}