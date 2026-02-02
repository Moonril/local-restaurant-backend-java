package it.moonril.local_restaurant_backend_java.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {

        super(message);
    }
}
