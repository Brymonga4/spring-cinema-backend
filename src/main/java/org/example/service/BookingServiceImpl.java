package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Booking;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.repository.BookingRepository;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Optional<Booking> findById(Long id) {

        return this.bookingRepository.findById(id);
    }

    @Override
    public Optional<Booking> findByStrId(String id) {
        return this.bookingRepository.findByStrId(id);
    }

    @Override
    public Booking save(Booking booking) {
        return this.bookingRepository.save(booking);
    }

    @Override
    public List<Booking> saveAll(List<Booking> bookings) {
        return this.bookingRepository.saveAll(bookings);
    }

    @Override
    public void deleteById(Long id) {
        this.bookingRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Booking update(Booking booking) {
        this.bookingRepository.findAndLockById(booking.getId());
        return this.bookingRepository.save(booking);
    }

}
