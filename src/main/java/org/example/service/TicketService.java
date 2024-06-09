package org.example.service;

import org.example.dto.FullTicketWithDetailsDTO;
import org.example.dto.MovieDTO;
import org.example.dto.TicketDTO;
import org.example.dto.TicketWithUserDTO;
import org.example.model.Movie;
import org.example.model.Ticket;
import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    //FIND
    Optional<Ticket> findById(Long id);

    // CREATE
    Ticket save(Ticket ticket);

    List<Ticket> saveAll(List<Ticket> tickets);

    void deleteById(Long id);
    void deleteAll();

    Ticket update(Ticket ticket);

    List<Ticket> convertToEntitiesNoSecure(TicketWithUserDTO ticketWithUserDTO);

    List<FullTicketWithDetailsDTO> generateBookingAndSaveTickets(List<Ticket> tickets, User user);

    int countTicketsByUserAndMovie(long userId, long movieId);

    List<Ticket> findTicketsByBookingIdAndAvailableIsTrue(String bookingId);

    List<FullTicketWithDetailsDTO> buyTicketNoSecurity(TicketWithUserDTO ticketWithUserDTO);

    void generatePDFandSendEmailToUserFromFullTickets(List<FullTicketWithDetailsDTO> fullTickets, String emailTo);
}
