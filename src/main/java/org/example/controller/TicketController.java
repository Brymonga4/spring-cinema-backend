package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.model.*;
import org.example.service.*;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.example.util.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;


    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id){

        return this.ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/ticket")
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket ticket){
        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }


    @PostMapping("/screenings/ticket/buyNoSecurity")
    public ResponseEntity<List<FullTicketWithDetailsDTO>> buyTicketNoSecurity(@Valid @RequestBody TicketWithUserDTO ticketWithUserDTO){

        List<FullTicketWithDetailsDTO> fullTickets = this.ticketService.buyTicketNoSecurity(ticketWithUserDTO);
        return ResponseEntity.ok(fullTickets);
    }


    @GetMapping("/user/{userId}/movie/{movieId}/tickets")
    public ResponseEntity<Integer> findAllByUserId(@PathVariable Long userId,@PathVariable Long movieId ){

        int ticketsBought = this.ticketService.countTicketsByUserAndMovie(userId, movieId);


        if (ticketsBought <= 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ticketsBought);

    }

}
