package it.moonril.local_restaurant_backend_java.controller;



import it.moonril.local_restaurant_backend_java.dto.LoginDto;
import it.moonril.local_restaurant_backend_java.dto.UserDto;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.models.User;
import it.moonril.local_restaurant_backend_java.repository.UserRepository;
import it.moonril.local_restaurant_backend_java.service.AuthService;
import it.moonril.local_restaurant_backend_java.service.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto,
                         BindingResult bindingResult) throws ValidationException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }
        return userService.saveUser(userDto);
    }

    @GetMapping("/api/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = optionalUser.get();

        UserDto userDto = new UserDto(
                user.getEmail(),
                user.getUsername(),
                ""
        );

        return ResponseEntity.ok(userDto);
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto,
                        BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage())
                    .reduce("",(e,s)->e+s));
        }


        return authService.login(loginDto);
    }
}
