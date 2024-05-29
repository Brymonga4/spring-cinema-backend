package org.example.service;

import org.example.dto.MovieDTO;
import org.example.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    //FIND
    List<MovieDTO> findAll();
    List<MovieDTO> findAllByYear(Integer year);
    Optional<MovieDTO> findById(Long id);
    Optional<MovieDTO> findMovieByTitle(String title);
    // CREATE
    MovieDTO save(Movie movie);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    MovieDTO update(Movie movie);

    //OTROS
    List<MovieDTO> findRecentMovies();

    List<MovieDTO> findMovieListing();

    MovieDTO convertToDto(Movie movie);

    Movie convertToEntity(MovieDTO movieDto);

}
