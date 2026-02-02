package it.moonril.local_restaurant_backend_java.exceptions;

public class DishAlreadyExistsException extends RuntimeException {
    public DishAlreadyExistsException(String dish) {
        super("This dish already exists: " + dish);
    }
}
