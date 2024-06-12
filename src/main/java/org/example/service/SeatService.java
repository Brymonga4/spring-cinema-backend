package org.example.service;

import org.example.dto.MovieDTO;
import org.example.dto.SeatDTO;
import org.example.dto.SeatTypeDTO;
import org.example.model.Movie;
import org.example.model.Seat;
import org.springframework.data.repository.query.Param;

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

    List<Seat> updateSeatsDTO(List<SeatTypeDTO> seatsTypeDTO);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Seat update(Seat seat);

    List<Seat> findAvailableSeatsByScreeningId(@Param("screeningId") Long screeningId);

    List<Seat> findUnavailableSeatsByScreeningId(@Param("screeningId") Long screeningId);

    List<SeatDTO> findAllSeatsOfScreeningId(Long screeningId);




}
