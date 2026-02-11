package it.moonril.local_restaurant_backend_java.service;


import it.moonril.local_restaurant_backend_java.dto.BookingDto;
import it.moonril.local_restaurant_backend_java.enums.BookingStatus;
import it.moonril.local_restaurant_backend_java.exceptions.NotFoundException;
import it.moonril.local_restaurant_backend_java.models.Booking;
import it.moonril.local_restaurant_backend_java.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;





    public Booking saveBooking(BookingDto bookingDto) throws NotFoundException {
        Booking booking = new Booking();

        booking.setName(bookingDto.getName());
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setNumberOfCustomers(bookingDto.getNumberOfCustomers());
        booking.setPreference(bookingDto.getPreference());
        booking.setEmail(bookingDto.getEmail());



        return bookingRepository.save(booking);
    }

    public Page<Booking> getAllBookings(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookingRepository.findAll(pageable);
    }

    public Booking getBooking(int id) throws NotFoundException{
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking with id: " + id + " not found"));
    }


    public Booking updateBooking(int id, BookingDto bookingDto) throws NotFoundException {
        Booking bookingToUpdate = getBooking(id);

        bookingToUpdate.setName(bookingToUpdate.getName());
        bookingToUpdate.setCheckInDate(bookingDto.getCheckInDate());
        bookingToUpdate.setNumberOfCustomers(bookingDto.getNumberOfCustomers());
        bookingToUpdate.setPreference(bookingDto.getPreference());
        bookingToUpdate.setEmail(bookingDto.getEmail());




        return bookingRepository.save(bookingToUpdate);
    }


    public Booking patchBooking(int id, BookingStatus newStatus) throws NotFoundException, IllegalArgumentException {
        Booking booking = getBooking(id);
        BookingStatus currentStatus = booking.getBookingStatus();

        Map<BookingStatus, List<BookingStatus>> validTransitions = Map.of(
                BookingStatus.PENDING, List.of(BookingStatus.CONFIRMED, BookingStatus.CANCELLED),
                BookingStatus.CONFIRMED, List.of(BookingStatus.CANCELLED, BookingStatus.COMPLETED),
                BookingStatus.CANCELLED, List.of(),
                BookingStatus.COMPLETED, List.of()
        );

        if (!validTransitions.get(currentStatus).contains(newStatus)) {
            throw new IllegalArgumentException("Transition from " + currentStatus + " to " + newStatus + " not allowed.");
        }


        booking.setBookingStatus(newStatus);


        return bookingRepository.save(booking);
    }

    public void deleteBooking(int id) throws NotFoundException{
        Booking bookingToDelete = getBooking(id);

        bookingRepository.delete(bookingToDelete);
    }


}
