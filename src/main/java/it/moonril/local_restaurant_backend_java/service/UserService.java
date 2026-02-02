package it.moonril.local_restaurant_backend_java.service;

import com.cloudinary.Cloudinary;
import it.moonril.local_restaurant_backend_java.dto.UserDto;
import it.moonril.local_restaurant_backend_java.enums.UserType;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.exceptions.UnauthorizedException;
import it.moonril.local_restaurant_backend_java.models.User;
import it.moonril.local_restaurant_backend_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;

    public User saveUser(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserType(UserType.ADMIN);

        return userRepository.save(user);
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id).
                orElseThrow(()-> new NotFoundException("User with id: "
                        + id + " not found"));
    }

    public Page<User> getAllUsers(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public User updateUser(int id, UserDto userDto)
            throws NotFoundException {

        User userAuthenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userAuthenticated.getUserType().name().equals("ADMIN") && userAuthenticated.getId() != id) {
            throw new UnauthorizedException("You cannot modify another user.");
        }

        User userToUpdate = getUser(id);

        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setUsername(userDto.getUsername());
        if (!passwordEncoder.matches(userDto.getPassword(), userToUpdate.getPassword())){
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }


        return userRepository.save(userToUpdate);
    }

    public String patchUser(int id, MultipartFile file) throws NotFoundException, IOException {
        User userToPatch = getUser(id);

        String url = (String)cloudinary.uploader().upload(file.getBytes(),
                Collections.emptyMap()).get("url");

        userToPatch.setAvatar(url);

        userRepository.save(userToPatch);

        return url;
    }


    public void deleteUser(int id) throws NotFoundException {
        User userAuthenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userAuthenticated.getUserType().name().equals("ADMIN") && userAuthenticated.getId() != id) {
            throw new UnauthorizedException("You cannot delete another user.");
        }

        User userToDelete = getUser(id);

        userRepository.delete(userToDelete);
    }

}
