package co.edu.umanizales.helpdesku.model;

import com.fasterxml.jackson.annotation.JsonCreator;


import co.edu.umanizales.helpdesku.exception.BadRequestException;

public enum PriorityLevel {

    LOW,
    MEDIUM,
    HIGH,
    CRITICAL;

    private static final String INVALID_MESSAGE = "Escribe una nivel de servicios correcto";

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PriorityLevel fromString(String rawValue) {
        if (rawValue == null) {
            throw new BadRequestException(INVALID_MESSAGE);
        }
        String sanitized = rawValue.strip();
        if (sanitized.isEmpty()) {
            throw new BadRequestException(INVALID_MESSAGE);
        }
        for (PriorityLevel option : values()) {
            if (option.name().equalsIgnoreCase(sanitized)) {
                return option;
            }
        }
        throw new BadRequestException(INVALID_MESSAGE);
    }

}
