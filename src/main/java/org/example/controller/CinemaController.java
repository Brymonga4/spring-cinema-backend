package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.Cinema;
import org.example.model.Seat;
import org.example.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CinemaController {

    private CinemaService service;

    public CinemaController(CinemaService service){
        this.service = service;
    }


    /*
    GET http://localhost:8080/api/cinemas
     */

    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> findAll(){

        List <Cinema> cinemas = this.service.findAll();

        if (cinemas.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cinemas);

    }

    /*
    GET http://localhost:8080/api/seats/1
     */
    @GetMapping("/cinemas/{id}")
    public ResponseEntity<Cinema> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/movies
     */

    @PostMapping("/cinemas")
    public ResponseEntity<Cinema> create(@Valid @RequestBody Cinema cinema){

        if(cinema.getId() != null)
            return ResponseEntity.badRequest().build();

        Cinema savedCinema = this.service.save(cinema);

        return ResponseEntity.ok(savedCinema);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/cinemas/{id}")
    public ResponseEntity<Cinema> update(@PathVariable Long id, @Valid @RequestBody Cinema cinema){

        System.out.println(this.service.findById(id).isEmpty());
        if(this.service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        cinema.setId(id);
        Cinema updatedCinema = this.service.update(cinema);

        return ResponseEntity.ok(updatedCinema);
    }

    @DeleteMapping ("/cinemas/{identifier}")
    public ResponseEntity<Seat> deleteById(@PathVariable("identifier") Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
