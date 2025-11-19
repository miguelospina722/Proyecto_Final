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
public class comment extends baseentity {

    private String ticketId;
    private String authorId;
    private String content;
    private LocalDateTime commentedAt;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(ticketId == null ? "" : ticketId);
        builder.append(",");
        builder.append(authorId == null ? "" : authorId);
        builder.append(",");
        builder.append(content == null ? "" : content);
        builder.append(",");
        if (commentedAt != null) {
            builder.append(commentedAt);
        }
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
            authorId = data[4];
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
