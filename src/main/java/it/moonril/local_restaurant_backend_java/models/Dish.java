package it.moonril.local_restaurant_backend_java.models;

import it.moonril.local_restaurant_backend_java.enums.DishType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_seq")
    @SequenceGenerator(name = "dish_seq", sequenceName = "dish_sequence", initialValue = 100, allocationSize = 1)
    private long id;

    @Column(unique = true)
    private String name;
    private String description;
    private String image;
    @Enumerated(EnumType.STRING)
    private DishType dishType;
}
