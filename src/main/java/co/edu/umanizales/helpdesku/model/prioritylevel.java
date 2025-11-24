package co.edu.umanizales.helpdesku.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

public enum prioritylevel {

    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;

    private static final String INVALID_MESSAGE = "Escribe una nivel de servicios correcto";

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static prioritylevel fromString(String rawValue) {
        if (rawValue == null) {
            throw new BadRequestException(INVALID_MESSAGE);
        }
        String sanitized = rawValue.strip();
        if (sanitized.isEmpty()) {
            throw new BadRequestException(INVALID_MESSAGE);
        }
        for (prioritylevel option : values()) {
            if (option.name().equalsIgnoreCase(sanitized)) {
                return option;
            }
        }
        throw new BadRequestException(INVALID_MESSAGE);
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
