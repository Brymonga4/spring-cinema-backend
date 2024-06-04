package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.FullTicketWithDetailsDTO;
import org.example.dto.MovieDTO;
import org.example.dto.TicketDTO;
import org.example.dto.TicketWithUserDTO;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private  final SeatRepository seatRepository;
    private  final ScreeningRepository screeningRepository;

    private final CinemaRepository cinemaRepository;
    private final ScreenRepository screenRepository;

    private final Screen_rowsRepository screenRowsRepository;


    private final MovieRepository movieRepository;
    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository, CinemaRepository cinemaRepository, ScreenRepository screenRepository, Screen_rowsRepository screenRowsRepository, MovieRepository movieRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.cinemaRepository = cinemaRepository;
        this.screenRepository = screenRepository;
        this.screenRowsRepository = screenRowsRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return this.ticketRepository.findById(id);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> saveAll(List<Ticket> tickets) {
        return this.ticketRepository.saveAll(tickets);
    }

    @Override
    public void deleteById(Long id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Ticket update(Ticket ticket) {
        this.ticketRepository.findAndLockById(ticket.getId());
        return this.ticketRepository.save(ticket);
    }

    @Override
    public TicketDTO convertToDto(Ticket ticket) {
        return new TicketDTO(
                ticket.getSeat().getIdSeat(),
                ticket.getScreening().getId()
        );
    }

    @Override
    public Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Seat seat = seatRepository.findById(ticketDTO.getSeat_id())
                .orElseThrow(() -> new EntityNotFoundException("No hay asiento con id : " + ticketDTO.getSeat_id()));
        Screening screening = screeningRepository.findById(ticketDTO.getScreening_id())
                .orElseThrow(() -> new EntityNotFoundException("No hay función con id : " + ticketDTO.getScreening_id()));

        if(screeningRepository.countFromNowToNext7Days(screening.getId())>0) {
            ticket.setScreening(screening);
        }else{
            throw new  RuntimeException("La función ya ha empezado");
        }

        ticket.setSeat(seat);
        ticket.setScreening(screening);
        return ticket;
    }

    @Override
    public Ticket convertToEntityNoSecure(TicketWithUserDTO ticketWithUserDTO) {

        return null;
    }

    @Override
    public double calculateTickePrice(Ticket ticket) {
        Screening screening = screeningRepository.findById(ticket.getScreening().getId())
                .orElseThrow(() -> new EntityNotFoundException("No hay función con id : " + ticket.getScreening().getId()));

        double seatType = ticket.getSeat().convert();
        return seatType * screening.getPrice();
    }

    @Override
    public List<Ticket> convertToEntitiesNoSecure(TicketWithUserDTO ticketWithUserDTO) {
        List <Ticket> tickets = new ArrayList<>();

        System.out.println (ticketWithUserDTO.getSeats().size());

        for(Long seatId : ticketWithUserDTO.getSeats()){
            System.out.println(seatId);
            Ticket ticket = new Ticket();

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new EntityNotFoundException("No hay asiento con id : " + seatId));
            System.out.println(seat);

            ScreenRows sr = screenRowsRepository.findById(seat.getScreenRows().getId())
                    .orElseThrow(() -> new RuntimeException("No hay fila con id : " + seat.getScreenRows().getId()));
            seat.setScreenRows(sr);

            System.out.println(sr);

            Screening screening = screeningRepository.findById(ticketWithUserDTO.getScreening_id())
                    .orElseThrow(() -> new EntityNotFoundException("No hay función con id : " + ticketWithUserDTO.getScreening_id()));
            Movie movie = movieRepository.findById(screening.getMovie().getId())
                    .orElseThrow(() -> new EntityNotFoundException("No hay pelicula con id : " + screening.getMovie().getId()));
            screening.setMovie(movie);

            System.out.println(screening);
            System.out.println(movie);

            Screen screen = screenRepository.findById(screening.getScreen().getId())
                    .orElseThrow(() -> new EntityNotFoundException("No hay sala con id : " + screening.getScreen().getId()));
            screening.setScreen(screen);

            Cinema cinema = cinemaRepository.findById(screen.getCinema().getId())
                    .orElseThrow(() -> new EntityNotFoundException("No hay cine con id : " + screen.getCinema().getId()));
            screen.setCinema(cinema);

            if(screeningRepository.countFromNowToNext7Days(screening.getId())>0) {
                ticket.setScreening(screening);
            }else{
                throw new  RuntimeException("La función ya ha empezado");
            }

            ticket.setSeat(seat);
            ticket.setScreening(screening);

            tickets.add(ticket);
        }


        return tickets;
    }

    @Override
    public int countTicketsByUserAndMovie(long userId, long movieId) {
        return this.ticketRepository.countTicketsByUserAndMovie(userId, movieId);
    }

    @Override
    public List<Ticket> findTicketsByBookingIdAndAvailableIsTrue(String bookingId) {
        return this.ticketRepository.findTicketsByBookingIdAndAvailableIsTrue(bookingId);
    }


}
