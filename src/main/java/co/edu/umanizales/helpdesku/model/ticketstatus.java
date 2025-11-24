package co.edu.umanizales.helpdesku.model;

import java.text.Normalizer;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

public enum ticketstatus {

    OPEN,
    ASSIGNED,
    IN_PROGRESS,
    ON_HOLD,
    RESOLVED,
    CLOSED;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ticketstatus fromString(String value) {
        if (value == null) {
            throw new BadRequestException("Escribe un estado correcto");
        }
        String sanitized = value.strip();
        if (sanitized.isEmpty()) {
            throw new BadRequestException("Escribe un estado correcto");
        }

        for (ticketstatus status : values()) {
            if (status.name().equalsIgnoreCase(sanitized)) {
                return status;
            }
        }

        String normalized = normalize(sanitized);
        ticketstatus aliasMatch = fromAlias(normalized);
        if (aliasMatch != null) {
            return aliasMatch;
        }

        throw new BadRequestException("Escribe un estado correcto");
    }

    private static String normalize(String input) {
        String upper = input.toUpperCase(Locale.ROOT);
        return Normalizer.normalize(upper, Normalizer.Form.NFD)
                .replaceAll("[^A-Z]+", " ")
                .strip();
    }

    private static ticketstatus fromAlias(String normalized) {
        return switch (normalized) {
            case "OPEN", "ABIERTO" -> OPEN;
            case "ASSIGNED", "ASIGNADO" -> ASSIGNED;
            case "IN PROGRESS", "IN_PROGRESS", "EN PROCESO" -> IN_PROGRESS;
            case "ON HOLD", "ON_HOLD", "EN ESPERA" -> ON_HOLD;
            case "RESOLVED", "RESUELTO" -> RESOLVED;
            case "CLOSED", "CERRADO" -> CLOSED;
            default -> null;
        };
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
