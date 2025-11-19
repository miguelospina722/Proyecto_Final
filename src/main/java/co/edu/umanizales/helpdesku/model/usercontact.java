package co.edu.umanizales.helpdesku.model;

public record usercontact(String email, String phoneNumber) {

    public String toCompact() {
        StringBuilder builder = new StringBuilder();
        if (email != null) {
            builder.append(email);
        }
        builder.append(";");
        if (phoneNumber != null) {
            builder.append(phoneNumber);
        }
        return builder.toString();
    }

    public static usercontact fromCompact(String compact) {
        if (compact == null) {
            return new usercontact(null, null);
        }
        String[] pieces = compact.split(";", -1);
        String parsedEmail = null;
        String parsedPhone = null;
        if (pieces.length > 0) {
            parsedEmail = pieces[0];
        }
        if (pieces.length > 1) {
            parsedPhone = pieces[1];
        }
        return new usercontact(parsedEmail, parsedPhone);
    }
}
