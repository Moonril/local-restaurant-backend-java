package it.moonril.local_restaurant_backend_java.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "restaurant_settings")
public class RestaurantSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_sequence", initialValue = 100, allocationSize = 1)
    private long id;

//    capacity
    private int maxCapacity;
    private int maxPartySize;

//    slot
    private int slotDurationMinutes;

//    opening time
    private LocalTime openingTime;
    private LocalTime closingTime;

//    opening days
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "restaurant_closed_days",
            joinColumns = @JoinColumn(name = "restaurant_settings_id")
    )
    @Column(name = "closed_day")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> closedDays;

// booking settings
    private int reservationAdvanceDays;
    private int minAdvanceMinutes;
    private int cancellationLimitHours;
}
