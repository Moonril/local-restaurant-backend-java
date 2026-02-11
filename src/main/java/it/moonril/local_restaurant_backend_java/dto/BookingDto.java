package it.moonril.local_restaurant_backend_java.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {

    @NotNull(message = "The field name cannot be empty")
    private String name;

    @NotNull(message = "The field checkInDate cannot be empty")
    @Future(message = "The check-in date cannot be in the past")
    private LocalDate checkInDate;


    @NotNull(message = "The field numberOfCustomers cannot be empty")
    @Min(1)
    @Max(4)
    private int numberOfCustomers;
    private String preference;
    @NotNull(message = "The field email cannot be empty")
    private String email;

}
