package co.edu.umanizales.helpdesku.model;

import java.time.LocalDateTime;
import java.util.function.Supplier;

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
        System.arraycopy(base, 0, result, 0, baseLength);
        if (extraLength > 0) {
            System.arraycopy(extras, 0, result, baseLength, extraLength);
        }
        return result;
    }

    protected static String idOrEmpty(baseentity entity) {
        return entity == null || entity.getId() == null ? "" : entity.getId();
    }

    protected static <T extends baseentity> T referenceFromId(String rawValue, Supplier<T> factory) {
        if (rawValue == null || rawValue.isBlank()) {
            return null;
        }
        T instance = factory.get();
        instance.setId(rawValue.trim());
        return instance;
    }

    protected static String valueAt(String[] data, int index) {
        return data.length > index ? data[index] : null;
    }

    public void onRemove() {
        // Default implementation does nothing. Subclasses may override to release resources.
    }
}
