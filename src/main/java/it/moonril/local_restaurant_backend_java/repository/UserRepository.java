package it.moonril.local_restaurant_backend_java.repository;


import it.moonril.local_restaurant_backend_java.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}

