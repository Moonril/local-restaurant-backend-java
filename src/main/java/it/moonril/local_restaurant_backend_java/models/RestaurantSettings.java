package it.moonril.local_restaurant_backend_java.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant_settings")
public class RestaurantSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_seq")
    @SequenceGenerator(name = "dish_seq", sequenceName = "dish_sequence", initialValue = 100, allocationSize = 1)
    private long id;

    private int maxCapacity;
    private int slotDurationMinutes;
    private int openingTime;
    private int closingTime;
}
