package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class priority extends baseentity {

    private String name;
    private prioritylevel level;
    private int responseMinutes;
    private int resolveMinutes;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(name == null ? "" : name);
        builder.append(",");
        builder.append(level == null ? "" : level.name());
        builder.append(",");
        builder.append(responseMinutes);
        builder.append(",");
        builder.append(resolveMinutes);
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            name = data[3];
        }
        if (data.length > 4 && data[4] != null && !data[4].isEmpty()) {
            level = prioritylevel.valueOf(data[4]);
        }
        if (data.length > 5 && data[5] != null && !data[5].isEmpty()) {
            responseMinutes = Integer.parseInt(data[5]);
        }
        if (data.length > 6 && data[6] != null && !data[6].isEmpty()) {
            resolveMinutes = Integer.parseInt(data[6]);
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("name", "level", "response_minutes", "resolve_minutes");
    }
}
