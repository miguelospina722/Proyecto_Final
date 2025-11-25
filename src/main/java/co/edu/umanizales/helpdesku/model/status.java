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
public class Status extends BaseEntity {

    private TicketStatus code;
    private String description;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(code == null ? "" : code.name());
        builder.append(",");
        builder.append(description == null ? "" : description);
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3) {
            code = parseCode(data[3]);
        }
        if (data.length > 4) {
            description = data[4];
        }
    }

    @Override
    public String[] headers() {
        return mergeHeaders("code", "description");
    }

    public void setCode(TicketStatus code) {
        if (code == null) {
            throw new BadRequestException("Asigna un estado correcto");
        }
        this.code = code;
    }

    public void setCode(String rawCode) {
        this.code = TicketStatus.fromString(rawCode);
    }

    private static TicketStatus parseCode(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return null;
        }
        return TicketStatus.fromString(rawValue);
    }
}
