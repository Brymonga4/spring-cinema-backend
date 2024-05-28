package org.example.service;

import org.example.dto.MovieDTO;
import org.example.model.Cinema;
import org.example.model.Movie;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    //FIND
    List<Cinema> findAll();
    Optional<Cinema> findById(Long id);
    // CREATE
    Cinema save(Cinema cinema);
    // DELETE
    void deleteById(Long id);

    // UPDATE
    Cinema update(Cinema cinema);
}
