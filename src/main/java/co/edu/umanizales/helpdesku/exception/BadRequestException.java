package co.edu.umanizales.helpdesku.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private final HttpStatus status;

    public BadRequestException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
