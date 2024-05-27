package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<MovieDTO> findAll() {

        return this.repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findRecentMovies() {
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        return this.repository.findRecentMovies(twoWeeksAgo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findMovieListing() {
        return this.repository.findMovieListingForNextTwoWeeks().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<MovieDTO> findAllByYear(Integer year) {
        return null;
    }

    @Override
    public Optional<MovieDTO> findById(Long id) {

        return this.repository.findById(id)
                .map(this::convertToDto);

    }

    @Override
    public Optional<MovieDTO> findMovieByTitle(String name) {

        Movie movie = this.repository.findMovieByTitle(name);
        return Optional.of(convertToDto(movie));
    }

    @Override
    public MovieDTO save(Movie movie) {
        MovieDTO movieDTO;
        if (repository.existsByTitleAndOrigTitle(movie.getTitle(), movie.getOrigTitle())) {
            System.out.println(movie.getTitle() + movie.getOrigTitle());
            throw new IllegalStateException("Ya existe una película con ese título y título original.");
        }
        else {
             movieDTO = convertToDto(this.repository.save(movie));
        }

        return movieDTO;
    }


    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
    }


    @Override
    @Transactional
    public MovieDTO update(Movie movie) {
        //Lo bloqueamos primero
        this.repository.findAndLockById(movie.getId());
        return this.save(movie);
    }



    private MovieDTO convertToDto(Movie movie) {

        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/movies/{id}")
                .buildAndExpand(movie.getId())
                .toUriString();

        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getOrigTitle(),
                movie.getRelease(),
                movie.getGenres(),
                movie.getActors(),
                movie.getDirectors(),
                movie.getScript(),
                movie.getProducers(),
                movie.getSynopsis(),
                movie.isOriginalVersion(),
                movie.isSpanishVersion(),
                movie.getImage(),
                movie.getTrailer(),
                movie.getAgeRating(),
                movie.getDuration(),
                baseUrl
        );
    }

    private Movie convertToEntity(MovieDTO movieDto) {
        Movie movie = repository.findById(movieDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("No hay película con id : " + movieDto.getId()));

        movie.setTitle(movieDto.getTitle());
        movie.setOrigTitle(movieDto.getOrigTitle());
        movie.setRelease(movieDto.getRelease());
        movie.setGenres(movieDto.getGenres());
        movie.setActors(movieDto.getActors());
        movie.setDirectors(movieDto.getDirectors());
        movie.setScript(movieDto.getScript());
        movie.setProducers(movieDto.getProducers());
        movie.setSynopsis(movieDto.getSynopsis());
        movie.setOriginalVersion(movieDto.isOriginalVersion());
        movie.setSpanishVersion(movieDto.isSpanishVersion());
        movie.setImage(movieDto.getImage());
        movie.setTrailer(movieDto.getTrailer());
        movie.setAgeRating(movieDto.getAgeRating());
        movie.setDuration(movieDto.getDuration());

        return movie;
    }


}
