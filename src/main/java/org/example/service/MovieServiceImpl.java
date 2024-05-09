package org.example.service;

import org.example.model.Movie;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Movie> findAll() {

        return this.repository.findAll();

    }

    @Override
    public List<Movie> findAllByYear(Integer year) {
        return null;
    }

    @Override
    public Optional<Movie> findById(Long id) {

        return this.repository.findById(id);

    }

    @Override
    public Optional<Movie> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Movie save(Movie movie) {
        this.repository.save(movie);
        return movie;
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }
}
