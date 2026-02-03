package it.moonril.local_restaurant_backend_java.controller;

import it.moonril.local_restaurant_backend_java.dto.DishDto;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.exceptions.ValidationException;
import it.moonril.local_restaurant_backend_java.models.Dish;
import it.moonril.local_restaurant_backend_java.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public Page<Dish> getAllDishes(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return dishService.getAllDishes(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable int id) throws NotFoundException {
        return dishService.getDish(id);
    }

    @GetMapping("/name")
    public List<Dish> getDishesByName(@PathVariable String name) throws NotFoundException {
        return dishService.getDishesByTitle(name);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dish saveDish(@RequestBody @Validated DishDto dishDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return dishService.saveDish(dishDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dish updateDish(@PathVariable int id, @RequestBody @Validated DishDto dishDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (e, s) -> e + s));
        }
        return dishService.updateDish(id, dishDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDish(@PathVariable int id) throws NotFoundException {
        dishService.deleteDish(id);
    }
    
}
