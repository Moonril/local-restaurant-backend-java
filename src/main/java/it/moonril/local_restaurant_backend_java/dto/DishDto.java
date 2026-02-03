package it.moonril.local_restaurant_backend_java.dto;

import it.moonril.local_restaurant_backend_java.enums.DishType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DishDto {
    @NotBlank(message = "The image field cannot be empty")
    private String image;
    @NotBlank(message = "The name field cannot be empty")
    private String name;
    @NotBlank(message = "The description field cannot be empty")
    private String description;
    @NotNull(message = "The field dishType cannot be empty")
    private DishType dishType;
}
