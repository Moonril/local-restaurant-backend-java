package it.moonril.local_restaurant_backend_java.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Email(message = "Email must have a valid format, es: indirizzo@gmail.com")
    private String email;
    @NotEmpty(message = "The username field cannot be empty")
    private String username;
    @NotEmpty(message = "The password field cannot be empty")
    private String password;

}
