package it.moonril.local_restaurant_backend_java.service;

import it.moonril.local_restaurant_backend_java.dto.DishDto;
import it.moonril.local_restaurant_backend_java.exceptions.DishAlreadyExistsException;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.models.Dish;
import it.moonril.local_restaurant_backend_java.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public Dish saveDish(DishDto dishDto) throws NotFoundException {
        Optional<Dish> existingDish = dishRepository.findByName(dishDto.getName());

        if (existingDish.isPresent()) {
            throw new DishAlreadyExistsException(dishDto.getName());
        }

        Dish dish = new Dish();

        dish.setImage(dishDto.getImage());
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setDishType(dishDto.getDishType());
        return dishRepository.save(dish);
    }

    public Page<Dish> getAllDishes(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dishRepository.findAll(pageable);
    }

    public Dish getDish(int id) throws NotFoundException{
        return dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Dish with id: " + id + " not found"));
    }

    public List<Dish> getDishesByTitle(String name) throws NotFoundException{
        List<Dish> dishes = dishRepository.findByNameContainingIgnoreCase(name);

        if (dishes.isEmpty()) {
            throw new NotFoundException("No dish found with this name: " + name);
        }

        return dishes;
    }

    public Dish updateDish(int id, DishDto dishDto) throws NotFoundException{
        Dish dishToUpdate = getDish(id);

        dishToUpdate.setImage(dishDto.getImage());
        dishToUpdate.setName(dishDto.getName());
        dishToUpdate.setDescription(dishDto.getDescription());
        dishToUpdate.setDishType(dishDto.getDishType());

        return dishRepository.save(dishToUpdate);
    }

    public void deleteDish(int id) throws NotFoundException{
        Dish dishToDelete = getDish(id);
        dishRepository.delete(dishToDelete);
    }


}
