package co.edu.umanizales.helpdesku.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class user extends baseentity {

    private String username;
    private String fullName;
    private String role;
    private boolean active;
    private usercontact contact;

    @Override
    public String toCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildBaseCsv());
        builder.append(",");
        builder.append(username == null ? "" : username);
        builder.append(",");
        builder.append(fullName == null ? "" : fullName);
        builder.append(",");
        builder.append(role == null ? "" : role);
        builder.append(",");
        builder.append(active);
        builder.append(",");
        if (contact != null) {
            builder.append(contact.toCompact());
        }
        return builder.toString();
    }

    @Override
    public void fromCsv(String csvLine) {
        String[] data = csvhelper.splitLine(csvLine);
        applyBaseValues(data);
        if (data.length > 3 && data[3] != null && !data[3].isEmpty()) {
            setUsername(data[3]);
        }
        if (data.length > 4) {
            fullName = data[4];
        }
        if (data.length > 5) {
            role = data[5];
        }
        if (data.length > 6) {
            active = Boolean.parseBoolean(data[6]);
        }
        if (data.length > 7) {
            contact = usercontact.fromCompact(data[7]);
        }
    }

    @Override
    public String[] headers() {
        String[] base = baseHeaders();
        String[] result = new String[base.length + 5];
        for (int index = 0; index < base.length; index++) {
            result[index] = base[index];
        }
        result[3] = "username";
        result[4] = "full_name";
        result[5] = "role";
        result[6] = "active";
        result[7] = "contact";
        return result;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
        String sanitized = username.trim();
        if (sanitized.isEmpty()) {
            throw new IllegalArgumentException("username must not be empty");
        }
        if (this.username != null && !this.username.equals(sanitized)) {
            throw new IllegalStateException("username cannot be reassigned once defined");
        }
        this.username = sanitized;
    }
}
