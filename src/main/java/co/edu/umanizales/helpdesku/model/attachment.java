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
public class attachment extends baseentity {

    private static final String TICKET_ERROR_MESSAGE = "Ticket no encontrado";

    private ticket ticket;
    private String fileName;
    private String filePath;
    private long sizeBytes;

    @Override
    public String toCsv() {
        validateTicket();
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(idOrEmpty(ticket));
        builder.append(",");
        builder.append(fileName == null ? "" : fileName);
        builder.append(",");
        builder.append(filePath == null ? "" : filePath);
        builder.append(",");
        builder.append(sizeBytes);
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
        if (data.length > 4) {
            fileName = data[4];
        }
        if (data.length > 5) {
            filePath = data[5];
        }
        if (data.length > 6 && data[6] != null && !data[6].isEmpty()) {
            sizeBytes = Long.parseLong(data[6]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "file_name", "file_path", "size_bytes");
    }
}
