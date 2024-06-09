package org.example.controller;

import jakarta.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.MovieDTO;
import org.example.mapper.MovieMapper;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.example.util.UploadConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.example.util.MovieTitleUtil.getFileExtension;
import static org.example.util.MovieTitleUtil.sanitizeTitle;


@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService service;


    public MovieController(MovieService service) {
        this.service = service;

    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> findAll(){

        List <MovieDTO> moviesDTO = this.service.findAll();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);

    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PostMapping(value = "/movies", consumes = "multipart/form-data")
    public ResponseEntity<MovieDTO> create(@Valid @RequestPart("movie") Movie movie,
                                           @RequestPart("file") MultipartFile file) {
        if (movie.getId() != null)
            return ResponseEntity.badRequest().build();

        Movie movieTosave = this.service.handleFileUpload(movie,file);
        MovieDTO movieSaved = this.service.save(movieTosave);

        return ResponseEntity.ok(movieSaved);
    }

    @PutMapping(value = "/movies/{id}", consumes = "multipart/form-data")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id,
                                           @Valid @RequestPart("movie") Movie movie,
                                           @RequestPart("file") MultipartFile file) {

        if (service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();

        movie.setId(id);
        MovieDTO updatedMovie = this.service.uptadeMovieAndCover(movie,file);

        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping ("/movies/{identifier}")
    public ResponseEntity<Movie> deleteById(@PathVariable("identifier") Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/movies/releases")
    public ResponseEntity<List<MovieDTO>> findRecentMovies(){

        List <MovieDTO> moviesDTO = this.service.findRecentMovies();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);
    }





}
