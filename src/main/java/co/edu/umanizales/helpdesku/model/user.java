package co.edu.umanizales.helpdesku.model;

import java.util.Arrays;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String username;
    private String fullName;
    private UserRole role;
    private boolean active;
    private UserContact contact;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder(buildBaseCsv());
        appendValue(builder, username);
        appendValue(builder, fullName);
        appendValue(builder, role == null ? null : role.name());
        appendValue(builder, Boolean.toString(active));
        builder.append(",");
        if (contact != null) {
            builder.append(contact.toCompact());
        }
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = CsvHelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3 && data[3] != null && !data[3].isEmpty()) {
            setUsername(data[3]);
        }
        if (data.length > 4) {
            fullName = data[4];
        }
        if (data.length > 5 && data[5] != null && !data[5].isBlank()) {
            role = UserRole.fromString(data[5]);
        }
        if (data.length > 6) {
            active = Boolean.parseBoolean(data[6]);
        }
        if (data.length > 7) {
            contact = UserContact.fromCompact(data[7]);
        }
    }

    @Override
    public String[] headers() {
        String[] base = baseHeaders();
        String[] result = Arrays.copyOf(base, base.length + 5);
        int offset = base.length;
        result[offset] = "username";
        result[offset + 1] = "full_name";
        result[offset + 2] = "role";
        result[offset + 3] = "active";
        result[offset + 4] = "contact";
        return result;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new BadRequestException("El username es obligatorio");
        }
        String sanitized = username.trim();
        if (sanitized.isEmpty()) {
            throw new BadRequestException("El username es obligatorio");
        }
        if (this.username != null && !this.username.equals(sanitized)) {
            throw new BadRequestException("El username no puede modificarse");
        }
        this.username = sanitized;
    }

    private void appendValue(StringBuilder builder, String value) {
        builder.append(",");
        builder.append(value == null ? "" : value);
    }
}
