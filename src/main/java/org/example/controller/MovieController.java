package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    /*
    GET http://localhost:8080/api/movies
     */

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> findAll(){

        List <MovieDTO> moviesDTO = this.service.findAll();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);

    }

    /*
    GET http://localhost:8080/api/movies/1
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/movies
     */

    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> create(@Valid @RequestBody Movie movie){

        if(movie.getId() != null)
            return ResponseEntity.badRequest().build();

        MovieDTO savedMovie = this.service.save(movie);

        return ResponseEntity.ok(savedMovie);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @Valid @RequestBody Movie movie){

        System.out.println(this.service.findById(id).isEmpty());
        if(this.service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        movie.setId(id);
        MovieDTO updatedMovie = this.service.update(movie);

        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping ("/movies/{identifier}")
    public ResponseEntity<Movie> deleteById(@PathVariable("identifier") Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /*
    OTROS

     */
    @GetMapping ("/movies/releases")
    public ResponseEntity<List<MovieDTO>> findRecentMovies(){

        List <MovieDTO> moviesDTO = this.service.findRecentMovies();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);
    }






}
