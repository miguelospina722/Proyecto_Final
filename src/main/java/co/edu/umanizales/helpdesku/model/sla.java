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
public class sla extends baseentity {

    private String ticketId;
    private LocalDateTime responseDeadline;
    private LocalDateTime resolutionDeadline;
    private boolean breached;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(ticketId == null ? "" : ticketId);
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

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            ticketId = data[3];
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
