package it.moonril.local_restaurant_backend_java.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantSettingsDto {

    //    capacity
    private int maxCapacity;
    private int maxPartySize;

    //    slot
    private int slotDurationMinutes;

    //    opening time
    private LocalTime openingTime;
    private LocalTime closingTime;

    //    opening days
    private Set<DayOfWeek> closedDays;

    // booking settings
    private int reservationAdvanceDays;
    private int minAdvanceMinutes;
    private int cancellationLimitHours;
}
