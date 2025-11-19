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
public class assignment extends baseentity {

    private String ticketId;
    private String technicianId;
    private LocalDateTime assignedAt;
    private String notes;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(ticketId == null ? "" : ticketId);
        builder.append(",");
        builder.append(technicianId == null ? "" : technicianId);
        builder.append(",");
        if (assignedAt != null) {
            builder.append(assignedAt);
        }
        builder.append(",");
        builder.append(notes == null ? "" : notes);
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
            technicianId = data[4];
        }
        if (data.length > 5 && data[5] != null && !data[5].isEmpty()) {
            assignedAt = LocalDateTime.parse(data[5]);
        }
        if (data.length > 6) {
            notes = data[6];
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "technician_id", "assigned_at", "notes");
    }
}
