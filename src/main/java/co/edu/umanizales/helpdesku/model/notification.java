package co.edu.umanizales.helpdesku.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class notification extends baseentity {

    private String ticketId;
    private String recipientId;
    private String message;
    private LocalDateTime sentAt;
    private boolean read;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(ticketId == null ? "" : ticketId);
        builder.append(",");
        builder.append(recipientId == null ? "" : recipientId);
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

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            ticketId = data[3];
        }
        if (data.length > 4) {
            recipientId = data[4];
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
