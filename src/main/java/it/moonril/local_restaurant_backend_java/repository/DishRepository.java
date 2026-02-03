package it.moonril.local_restaurant_backend_java.repository;

import it.moonril.local_restaurant_backend_java.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer>, PagingAndSortingRepository<Dish, Integer> {
    public Optional<Dish> findByName(String name);
    @Query("SELECT r FROM Dish r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Dish> findByNameContainingIgnoreCase(@Param("name") String name);
}
