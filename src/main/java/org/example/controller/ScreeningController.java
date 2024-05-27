package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.model.Screening;
import org.example.service.ScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScreeningController {

    private ScreeningService service;

    public ScreeningController(ScreeningService service){
        this.service = service;
    }

     /*
    GET http://localhost:8080/api/screenings
     */

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> findAll(){

        List <Screening> screenings = this.service.findAll();

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/screenings/{id}")
    public ResponseEntity<Screening> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

       /*
    POST http://localhost:8080/api/movies
     */

    @PostMapping("/screenings")
    public ResponseEntity<Screening> create(@Valid @RequestBody Screening screening){

        if(screening.getId() != null)
            return ResponseEntity.badRequest().build();

        Screening savedScreening = this.service.save(screening);

        return ResponseEntity.ok(savedScreening);
    }

    /*
      PUT http://localhost:8080/api/movies
       */
    @PutMapping("/screenings/{id}")
    public ResponseEntity<Screening> update(@PathVariable Long id, @Valid @RequestBody Screening screening){


        if(this.service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        screening.setId(id);
        Screening updatedScreening = this.service.update(screening);

        return ResponseEntity.ok(updatedScreening);
    }

    @DeleteMapping ("/screenings/{id}")
    public ResponseEntity<Screening> deleteById(@PathVariable Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
