package co.edu.umanizales.helpdesku.model;

import co.edu.umanizales.helpdesku.exception.BadRequestException;

public record usercontact(String email, String phoneNumber) {

    public usercontact {
        String sanitizedEmail = email == null ? null : email.trim();
        if (sanitizedEmail == null || sanitizedEmail.isEmpty() || !sanitizedEmail.contains("@")) {
            throw new BadRequestException("Debe poner un correo válido.");
        }
        String sanitizedPhone = phoneNumber == null ? null : phoneNumber.trim();
        email = sanitizedEmail;
        phoneNumber = sanitizedPhone == null || sanitizedPhone.isEmpty() ? null : sanitizedPhone;
    }

    public String toCompact() {
        StringBuilder builder = new StringBuilder();
        builder.append(email);
        builder.append(";");
        if (phoneNumber != null) {
            builder.append(phoneNumber);
        }
        return builder.toString();
    }

    public static usercontact fromCompact(String compact) {
        if (compact == null || compact.isBlank()) {
            return null;
        }
        String[] pieces = compact.split(";", -1);
        String parsedEmail = pieces.length > 0 ? pieces[0].trim() : null;
        String parsedPhone = pieces.length > 1 ? pieces[1].trim() : null;
        if (parsedEmail == null || parsedEmail.isEmpty()) {
            return null;
        }
        return new usercontact(parsedEmail, parsedPhone);
    }
}
