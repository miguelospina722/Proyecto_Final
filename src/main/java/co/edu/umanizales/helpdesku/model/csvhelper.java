package co.edu.umanizales.helpdesku.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class csvhelper {

    private static final String DEFAULT_SEPARATOR = ",";
    private static final String COLLECTION_SEPARATOR = "|";

    public static String joinValues(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < values.size(); index++) {
            String value = values.get(index);
            if (value != null) {
                builder.append(value);
            }
            if (index < values.size() - 1) {
                builder.append(COLLECTION_SEPARATOR);
            }
        }
        return builder.toString();
    }

    public static List<String> splitValues(String raw) {
        List<String> results = new ArrayList<>();
        if (raw == null || raw.isEmpty()) {
            return results;
        }
        String[] tokens = raw.split(java.util.regex.Pattern.quote(COLLECTION_SEPARATOR));
        for (int index = 0; index < tokens.length; index++) {
            results.add(tokens[index]);
        }
        return results;
    }

    public static String[] splitLine(String line) {
        if (line == null) {
            return new String[0];
        }
        return line.split(DEFAULT_SEPARATOR, -1);
    }
}
