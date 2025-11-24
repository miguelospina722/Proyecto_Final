package co.edu.umanizales.helpdesku.model;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class category extends baseentity {

    private categoryname name;
    private String description;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(name == null ? "" : name.name());
        builder.append(",");
        builder.append(description == null ? "" : description);
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            name = parseName(data[3]);
        }
        if (data.length > 4) {
            description = data[4];
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("name", "description");
    }

    public void setName(categoryname name) {
        if (name == null) {
            throw new BadRequestException("Asigna una categoría correcta");
        }
        this.name = name;
    }

    public void setName(String rawName) {
        this.name = categoryname.fromString(rawName);
    }

    private static categoryname parseName(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return null;
        }
        return categoryname.fromString(rawValue);
    }
}
