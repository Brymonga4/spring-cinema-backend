package org.example.service;

import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.model.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatService {

    //FIND
    List<Seat> findAll();

    List<Seat> findAllSeatsInScreen(Long id);
    Optional<Seat> findById(Long id);
    // CREATE
    Seat save(Seat seat);

    List<Seat> saveAll(List<Seat> seats);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Seat update(Seat seat);



}
