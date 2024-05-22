package org.example.service;

import org.example.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> findAll();
    List<Movie> findAllByYear(Integer year);

    Optional<Movie> findById(Long id);

    Optional<Movie> findByName(String name);

    // CREATE

    Movie save(Movie movie);

    // DELETE

    void deleteById(Long id);
    void deleteAll();


    // Más cosas


}
