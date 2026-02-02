package it.moonril.local_restaurant_backend_java.service;


import it.moonril.local_restaurant_backend_java.dto.LoginDto;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.models.User;
import it.moonril.local_restaurant_backend_java.repository.UserRepository;
import it.moonril.local_restaurant_backend_java.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                ()->new NotFoundException("Cannot find user with these username/password"));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            return jwtTool.createToken(user);
        }else {
            throw new NotFoundException("Cannot find user with these username/password");
        }


    }
}
