package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.TicketDTO;
import org.example.model.*;
import org.example.service.*;
import org.example.util.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final BookingService bookingService;
    private final SeatService seatService;

    private final UserService userService;

    public TicketController(TicketService ticketService, ScreeningService screeningService, BookingService bookingService, SeatService seatService, UserService userService){
        this.ticketService = ticketService;
        this.screeningService = screeningService;
        this.bookingService = bookingService;
        this.seatService = seatService;
        this.userService = userService;
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id){

        return this.ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/ticket")
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket ticket){

        if(ticket.getBooking().getId()!=null){
            Booking booking = bookingService.findByStrId(ticket.getBooking().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva"));
        }
        if(ticket.getSeat().getIdSeat()!=null){
            Seat seat = seatService.findById(ticket.getSeat().getIdSeat())
                    .orElseThrow(() -> new RuntimeException("No se encontró el asiento"));
        }
        if(ticket.getScreening().getId()!=null){
            Screening screening = screeningService.findById(ticket.getScreening().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la función"));
        }


        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }

    @PostMapping("/screenings/{id}/ticket/buy")
    public ResponseEntity<Ticket> buyTicketFromScreeningById(@Valid @RequestBody TicketDTO ticketDTO){

        System.out.println(ticketDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ticket ticket = this.ticketService.convertToEntity(ticketDTO);

        if(user.getId()!=null){
            Booking booking = new Booking();
            System.out.println(booking);
            booking.setId(IdGenerator.randomString(9));
            booking.setUser(user);
            booking = bookingService.save(booking);

            ticket.setBooking(booking);
        }

        System.out.println(ticket);

        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }

}
