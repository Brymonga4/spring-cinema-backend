package org.example.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<Movie>> findAll(){

        List <Movie> movies = this.service.findAll();

        if (movies.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(movies);

    }

    @GetMapping("/allmovies")
    public  ResponseEntity<Map<String, Object>> findAllMovies(){
        List<Movie> movies = service.findAll();

        if (movies.isEmpty())
            return ResponseEntity.notFound().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("info", Map.of(
                "count", movies.size()
        ));
        response.put("results", movies);
        response.put("url", location.toString());

        return ResponseEntity.ok(response);
    }

    /*
    GET http://localhost:8080/api/movies/1
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/movies
     */

    @PostMapping("/movies")
    public ResponseEntity<Movie> create(@Valid @RequestBody Movie movie){

        if(movie.getId() != null)
            return ResponseEntity.badRequest().build();

        Movie savedMovie = this.service.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/movies")
    public ResponseEntity<Movie> update(@RequestBody Movie movie){

        //Comprobamos
        this.service.save(movie);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping ("/movies/{identifier}")
    public ResponseEntity<Movie> deleteById(@PathVariable("identifier") Long id){

        //Comprobacines
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
