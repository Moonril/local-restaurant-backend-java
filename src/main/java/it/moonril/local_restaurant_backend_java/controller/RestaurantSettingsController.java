package it.moonril.local_restaurant_backend_java.controller;

import it.moonril.local_restaurant_backend_java.dto.RestaurantSettingsDto;

import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.exceptions.ValidationException;
import it.moonril.local_restaurant_backend_java.models.RestaurantSettings;

import it.moonril.local_restaurant_backend_java.service.RestaurantSettingsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/restaurant-settings")
public class RestaurantSettingsController {

    @Autowired
    private RestaurantSettingsService settingsService;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantSettings getRestaurantSettingsById(@PathVariable int id) throws NotFoundException {
        return settingsService.getSettings(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantSettings saveRestaurantSettings(@RequestBody @Validated RestaurantSettingsDto settingsDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }

        return settingsService.saveSettings(settingsDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantSettings updateRestaurantSettings(@PathVariable int id, @RequestBody @Validated RestaurantSettingsDto settingsDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return settingsService.updateSettings(id, settingsDto);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRestaurantSettings(@PathVariable int id) throws NotFoundException {
        settingsService.deleteSettings(id);
    }
}
