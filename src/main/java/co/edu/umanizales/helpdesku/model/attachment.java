package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends BaseEntity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";

    private Ticket ticket;
    private String fileName;
    private String filePath;
    private long sizeBytes;

    @Override
    public String toCsv() {
        validateTicket();
        StringBuilder builder = new StringBuilder(buildBaseCsv());
        appendEntityId(builder, ticket);
        appendString(builder, fileName);
        appendString(builder, filePath);
        appendNullable(builder, sizeBytes);
        return builder.toString();
    }

    private void validateTicket() {
        if (ticket == null || ticket.getId() == null || ticket.getId().isBlank()) {
            throw new BadRequestException(TICKET_ERROR_MESSAGE);
        }
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        ticket = referenceFromId(valueAt(data, 3), Ticket::new);
        fileName = valueAt(data, 4);
        filePath = valueAt(data, 5);
        Long parsedSize = parseLong(valueAt(data, 6));
        sizeBytes = parsedSize == null ? 0L : parsedSize;
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "file_name", "file_path", "size_bytes");
    }
}
