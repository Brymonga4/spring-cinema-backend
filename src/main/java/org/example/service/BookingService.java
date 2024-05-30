package org.example.service;

import org.example.model.Booking;
import org.example.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    //FIND
    Optional<Booking> findById(Long id);
    Optional<Booking> findByStrId(String id);
    // CREATE
    Booking save(Booking booking);

    List<Booking> saveAll(List<Booking> bookings);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Booking update(Booking booking);


}
