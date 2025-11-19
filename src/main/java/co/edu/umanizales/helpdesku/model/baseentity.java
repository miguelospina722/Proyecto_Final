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
public abstract class baseentity implements csvpersistable {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected String buildBaseCsv() {
        StringBuilder builder = new StringBuilder();
        builder.append(id == null ? "" : id);
        builder.append(",");
        if (createdAt != null) {
            builder.append(createdAt);
        }
        builder.append(",");
        if (updatedAt != null) {
            builder.append(updatedAt);
        }
        return builder.toString();
    }

    protected void applyBaseValues(String[] data) {
        if (data.length > 0) {
            id = data[0];
        }
        if (data.length > 1 && data[1] != null && !data[1].isEmpty()) {
            createdAt = LocalDateTime.parse(data[1]);
        }
        if (data.length > 2 && data[2] != null && !data[2].isEmpty()) {
            updatedAt = LocalDateTime.parse(data[2]);
        }
    }

    protected String[] baseHeaders() {
        return new String[]{"id", "created_at", "updated_at"};
    }

    protected String[] mergeHeaders(String... extras) {
        String[] base = baseHeaders();
        int baseLength = base.length;
        int extraLength = extras == null ? 0 : extras.length;
        String[] result = new String[baseLength + extraLength];
        for (int index = 0; index < baseLength; index++) {
            result[index] = base[index];
        }
        if (extras != null) {
            for (int index = 0; index < extraLength; index++) {
                result[baseLength + index] = extras[index];
            }
        }
        return result;
    }

    public void onRemove() {
        // Default implementation does nothing. Subclasses may override to release resources.
    }
}
