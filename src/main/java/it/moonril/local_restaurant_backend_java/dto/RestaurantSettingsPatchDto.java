package it.moonril.local_restaurant_backend_java.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantSettingsPatchDto {

    private LocalTime openingTime;
    private LocalTime closingTime;
    private Set<DayOfWeek> closedDays;
    private Integer maxCapacity;
    private Integer maxPartySize;
    private Integer slotDurationMinutes;
    private Integer reservationAdvanceDays;
    private Integer minAdvanceMinutes;
    private Integer cancellationLimitHours;
}
