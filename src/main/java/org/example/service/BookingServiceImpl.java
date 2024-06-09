package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.FullTicketWithDetailsDTO;
import org.example.dto.MovieDTO;
import org.example.dto.ScreeningDTO;
import org.example.dto.ScreeningDayAndHourDTO;
import org.example.exception.Exceptions;
import org.example.mapper.MovieMapper;
import org.example.mapper.ScreeningMapper;
import org.example.mapper.TicketMapper;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final Screen_rowsRepository screenRowsRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, TicketRepository ticketRepository, SeatRepository seatRepository, Screen_rowsRepository screenRowsRepository, ScreeningRepository screeningRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.screenRowsRepository = screenRowsRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
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

    @Override
    @Transactional
    public List<FullTicketWithDetailsDTO> validateBooking(String identifier){
        // Recuperamos todos los tickets con un determinado identificador y que aún no hayan sido validados
        List <Ticket> ticketsAvailable = this.ticketRepository.findTicketsByBookingIdAndAvailableIsTrue(identifier);

        // Lista que usaremos para los DTOS
        List<FullTicketWithDetailsDTO> fullTickets = new ArrayList<>();

        Booking booking = this.bookingRepository.findByStrId(identifier)
                .orElseThrow(()-> new Exceptions.BookingNotFoundException(identifier));

        for (Ticket t : ticketsAvailable){

            t.setAvailable(false);

            // El usuario del ticket
            User user = this.userRepository.findById(booking.getUser().getId())
                    .orElseThrow(()-> new Exceptions.UserNotFoundException(booking.getUser().getId().toString()));

            t.getBooking().setUser(user);

            // La silla y la fila del ticket
            Seat seat = this.seatRepository.findById(t.getSeat().getIdSeat())
                    .orElseThrow(()-> new Exceptions.SeatNotFoundException(t.getSeat().getIdSeat().toString()));
            ScreenRows sr = this.screenRowsRepository.findById(t.getSeat().getScreenRows().getId())
                    .orElseThrow(()-> new Exceptions.RowNotFoundException(t.getSeat().getScreenRows().getId().toString()));

            // Asignamos la fila a la silla y la silla al ticket
            seat.setScreenRows(sr);
            t.setSeat(seat);

            // La función y la película de esa función del ticket
            Screening screening = this.screeningRepository.findById(t.getScreening().getId())
                    .orElseThrow(()-> new Exceptions.ScreeningNotFoundException(t.getScreening().getId().toString()));
            Movie movie = this.movieRepository.findById(screening.getMovie().getId())
                    .orElseThrow(()-> new Exceptions.MovieNotFoundException(screening.getMovie().getId().toString()));
            // Guardamos la peli en la función y la función en el ticket
            screening.setMovie(movie);
            t.setScreening(screening);

            fullTickets.add(TicketMapper.toFullTicketWithDetailsDTO(t));

            this.ticketRepository.save(t);
        }

        return fullTickets;

    }




}
