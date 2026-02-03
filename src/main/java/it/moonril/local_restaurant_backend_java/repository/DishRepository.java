package it.moonril.local_restaurant_backend_java.repository;

import it.moonril.local_restaurant_backend_java.models.Dish;
import it.moonril.local_restaurant_backend_java.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer>, PagingAndSortingRepository<Dish, Integer> {
    public Optional<User> findByName(String name);
}
