package co.edu.umanizales.helpdesku.model;

import java.text.Normalizer;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

public enum CategoryName {

    INFRASTRUCTURE,
    HARDWARE,
    SOFTWARE,
    APPS,
    SERVICES;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CategoryName fromString(String value) {
        if (value == null) {
            throw new BadRequestException("Asigna una categoría correcta");
        }
        String sanitized = value.strip();
        if (sanitized.isEmpty()) {
            throw new BadRequestException("Asigna una categoría correcta");
        }

        for (CategoryName option : values()) {
            if (option.name().equalsIgnoreCase(sanitized)) {
                return option;
            }
        }

        String normalized = normalize(sanitized);
        CategoryName aliasMatch = fromAlias(normalized);
        if (aliasMatch != null) {
            return aliasMatch;
        }

        throw new BadRequestException("Asigna una categoría correcta");
    }

    private static String normalize(String input) {
        String uppercased = input.toUpperCase(Locale.ROOT);
        return Normalizer.normalize(uppercased, Normalizer.Form.NFD)
                .replaceAll("[^A-Z]+", " ")
                .strip();
    }

    private static CategoryName fromAlias(String normalized) {
        return switch (normalized) {
            case "INFRAESTRUCTURA", "INFRASTRUCTURE" -> INFRASTRUCTURE;
            case "HARDWARE" -> HARDWARE;
            case "SOFTWARE" -> SOFTWARE;
            case "APLICACIONES", "APLICACION", "APPS", "APP" -> APPS;
            case "SERVICIOS", "SERVICIO", "SERVICES" -> SERVICES;
            default -> null;
        };
    }

}
