package it.moonril.local_restaurant_backend_java.repository;

import it.moonril.local_restaurant_backend_java.models.Dish;
import it.moonril.local_restaurant_backend_java.models.RestaurantSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RestaurantSettingsRepository extends JpaRepository<RestaurantSettings, Integer>, PagingAndSortingRepository<RestaurantSettings, Integer> {
    Optional<RestaurantSettings> findTopByOrderByIdAsc();
}
