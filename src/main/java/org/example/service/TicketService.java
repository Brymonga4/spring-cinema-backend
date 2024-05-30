package org.example.service;

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

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Ticket update(Ticket ticket);
}
