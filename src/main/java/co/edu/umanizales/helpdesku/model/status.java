package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class status extends baseentity {

    private String code;
    private String name;
    private String description;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(code == null ? "" : code);
        builder.append(",");
        builder.append(name == null ? "" : name);
        builder.append(",");
        builder.append(description == null ? "" : description);
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            code = data[3];
        }
        if (data.length > 4) {
            name = data[4];
        }
        if (data.length > 5) {
            description = data[5];
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("code", "name", "description");
    }
}
