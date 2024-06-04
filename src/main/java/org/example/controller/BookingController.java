package org.example.controller;

import org.example.dto.FullTicketWithDetailsDTO;
import org.example.dto.MovieDTO;
import org.example.dto.ScreeningDTO;
import org.example.dto.ScreeningDayAndHourDTO;
import org.example.model.*;
import org.example.service.*;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final BookingService bookingService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final UserService userService;

    private final Screen_rowsService screenRowsService;

    public BookingController(BookingService bookingService, TicketService ticketService, ScreeningService screeningService, SeatService seatService, MovieService movieService, UserService userService, Screen_rowsService screenRowsService){
        this.bookingService = bookingService;
        this.ticketService = ticketService;
        this.screeningService = screeningService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.userService = userService;
        this.screenRowsService = screenRowsService;
    }

    @PostMapping("/bookings/validate")
    public ResponseEntity<List<FullTicketWithDetailsDTO>> validateBooking(@RequestBody String identifier ){

        List <Ticket> ticketsAvailable = this.ticketService.findTicketsByBookingIdAndAvailableIsTrue(identifier);

        Booking booking = this.bookingService.findByStrId(identifier)
                .orElseThrow(()-> new RuntimeException("No existe esa reserva"));

        List<FullTicketWithDetailsDTO> fullTickets = new ArrayList<>();

        for (Ticket t : ticketsAvailable){

            t.setAvailable(false);

            User user = this.userService.findById(booking.getUser().getId())
                    .orElseThrow(()-> new RuntimeException("No existe ese usuario"));
            t.getBooking().setUser(user);
            Seat seat = this.seatService.findById(t.getSeat().getIdSeat())
                            .orElseThrow(()-> new RuntimeException("No existe esa butaca"));
            ScreenRows sr = this.screenRowsService.findById(t.getSeat().getScreenRows().getId())
                    .orElseThrow(()-> new RuntimeException("No existe esa fila"));

            seat.setScreenRows(sr);
            t.setSeat(seat);

            Screening screening = this.screeningService.findById(t.getScreening().getId())
                    .orElseThrow(()-> new RuntimeException("No existe esa funcion"));
            MovieDTO movieDTO = this.movieService.findById(screening.getMovie().getId())
                    .orElseThrow(()-> new RuntimeException("No existe ese usuario"));
            screening.setMovie(this.movieService.convertToEntity(movieDTO));
            t.setScreening(screening);

            ScreeningDayAndHourDTO sdahDTO = this.screeningService.toScreeningDayAndHourDTO(t.getScreening().getStart_time());
            double ticketPrice = this.ticketService.calculateTickePrice(t);

            FullTicketWithDetailsDTO fullTicket =
                    FullTicketWithDetailsDTO.builder()
                            .identifier(t.getBooking().getId())
                            .screeningDTO(ScreeningDTO.builder()
                                    .Id(t.getScreening().getId())
                                    .screen(Math.toIntExact(t.getScreening().getScreen().getId()))
                                    .movieTitle(t.getScreening().getMovie().getTitle())
                                    .screeningDayAndHourDTO(sdahDTO)
                                    .cinemaName(t.getScreening().getScreen().getCinema().getName())
                                    .screenPrice(t.getScreening().getPrice())
                                    .audio(t.getScreening().getAudio())
                                    .build())
                            .rowAndSeat("F "+t.getSeat().getScreenRows().getRowNumber() +" B "+ t.getSeat().getSeatNumber())
                            .totalPrice(ticketPrice)
                            .build();

            fullTickets.add(fullTicket);

            this.ticketService.save(t);

        }


        return ResponseEntity.ok(fullTickets);

    }


}
