package it.moonril.local_restaurant_backend_java.models;

import it.moonril.local_restaurant_backend_java.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
    @SequenceGenerator(name = "booking_seq", sequenceName = "booking_sequence", initialValue = 10000, allocationSize = 1)
    private long id;

    private String name;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime bookingCreationDate;
    private LocalDate checkInDate;

    private int numberOfCustomers;
    private String preference;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.PENDING;
}
