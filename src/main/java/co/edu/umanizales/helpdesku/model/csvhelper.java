package co.edu.umanizales.helpdesku.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvHelper {

    private static final String DEFAULT_SEPARATOR = ",";

    public static String[] splitLine(String line) {
        if (line == null) {
            return new String[0];
        }
        return line.split(DEFAULT_SEPARATOR, -1);
    }
}
