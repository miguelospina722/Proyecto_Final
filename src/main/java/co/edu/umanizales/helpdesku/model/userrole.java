package co.edu.umanizales.helpdesku.model;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

public enum UserRole {

    ADMIN,
    TECHNICIAN,
    AGENT,
    USER;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserRole fromString(String value) {
        if (value == null) {
            throw new BadRequestException("Asigna un rol correcto");
        }
        String sanitized = value.strip();
        if (sanitized.isEmpty()) {
            throw new BadRequestException("Asigna un rol correcto");
        }

        for (UserRole role : values()) {
            if (role.name().equalsIgnoreCase(sanitized)) {
                return role;
            }
        }

        String normalized = sanitized.replace('Á', 'A')
                .replace('É', 'E')
                .replace('Í', 'I')
                .replace('Ó', 'O')
                .replace('Ú', 'U')
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u');
        String[] tokens = normalized.split("[^A-Za-z]+");
        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }
            UserRole aliasMatch = fromAlias(token);
            if (aliasMatch != null) {
                return aliasMatch;
            }
        }

        throw new BadRequestException("Asigna un rol correcto");
    }

    private static UserRole fromAlias(String token) {
        return switch (token.toUpperCase(Locale.ROOT)) {
            case "ADMIN","ADMINISTRADOR" -> ADMIN;
            case "TECHNICIAN", "TECH", "TECNICO", "TÉCNICO" -> TECHNICIAN;
            case "AGENT", "AGENTE" -> AGENT;
            case "USER", "USUARIO" -> USER;
            default -> null;
        };
    }

}
