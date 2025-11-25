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
public class Assignment extends BaseEntity {

    private Ticket ticket;
    private User technician;
    private LocalDateTime assignedAt;
    private String notes;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder(buildBaseCsv());
        appendEntityId(builder, ticket);
        appendEntityId(builder, technician);
        appendNullable(builder, assignedAt);
        appendString(builder, notes);
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        ticket = parseReference(data, 3, Ticket::new);
        technician = parseReference(data, 4, User::new);
        assignedAt = parseDateTime(valueAt(data, 5));
        notes = valueAt(data, 6);
    }

    @Override
    public String[] headers() {
        return mergeHeaders("ticket_id", "technician_id", "assigned_at", "notes");
    }
}
