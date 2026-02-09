package it.moonril.local_restaurant_backend_java.repository;


import it.moonril.local_restaurant_backend_java.enums.BookingStatus;
import it.moonril.local_restaurant_backend_java.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>,
        PagingAndSortingRepository<it.moonril.local_restaurant_backend_java.models.Booking, Integer> {
    public List<Booking> findByBookingStatus(BookingStatus bookingStatus);

//    List<Booking> findOverlappingBookings(
//            @Param("accommodationId") int accommodationId,
//            @Param("checkIn") LocalDate checkIn,
//            @Param("checkOut") LocalDate checkOut
//    );
}
