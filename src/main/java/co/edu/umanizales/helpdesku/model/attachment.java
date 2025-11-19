package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class attachment extends baseentity {

    private String ticketId;
    private String fileName;
    private String filePath;
    private long sizeBytes;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(ticketId == null ? "" : ticketId);
        builder.append(",");
        builder.append(fileName == null ? "" : fileName);
        builder.append(",");
        builder.append(filePath == null ? "" : filePath);
        builder.append(",");
        builder.append(sizeBytes);
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
