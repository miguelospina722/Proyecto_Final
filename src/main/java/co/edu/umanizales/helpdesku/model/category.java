package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class category extends baseentity {

    private String name;
    private String description;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
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
            name = data[3];
        }
        if (data.length > 4) {
            description = data[4];
        }
    }

    @Override
    public String[] headers() {
        String[] base = baseHeaders();
        String[] result = new String[base.length + 2];
        for (int index = 0; index < base.length; index++) {
            result[index] = base[index];
        }
        result[3] = "name";
        result[4] = "description";
        return result;
    }
}
