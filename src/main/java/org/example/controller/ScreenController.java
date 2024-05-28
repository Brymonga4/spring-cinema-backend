package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.model.Cinema;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.service.CinemaService;
import org.example.service.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScreenController {
    private ScreenService screenService;
    private CinemaService cinemaService;

    public ScreenController(ScreenService screenService, CinemaService cinemaService) {
        this.screenService = screenService;
        this.cinemaService = cinemaService;
    }

    /*
    GET http://localhost:8080/api/screens
     */

    @GetMapping("/screens")
    public ResponseEntity<List<Screen>> findAll(){

        List <Screen> screens = this.screenService.findAll();

        if (screens.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screens);

    }

    /*
    GET http://localhost:8080/api/screens/1
     */
    @GetMapping("/screens/{id}")
    public ResponseEntity<Screen> findById(@PathVariable Long id){

        return this.screenService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

     /*
    POST http://localhost:8080/api/screens
     */

    @PostMapping("/screens")
    public ResponseEntity<Screen> create(@Valid @RequestBody Screen screen){

        if(screen.getCinema().getId() != null) {
            Cinema cinema = cinemaService.findById(screen.getCinema().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el cine"));
            screen.setCinema(cinema);
        }

        Screen savedScreen = this.screenService.save(screen);
        return ResponseEntity.ok(savedScreen);
    }

    /*
    PUT http://localhost:8080/api/screens/1
     */
    @PutMapping("/screens/{id}")
    public ResponseEntity<Screen> update(@PathVariable Long id, @Valid @RequestBody Screen screen){

        if(this.screenService.findById(id).isEmpty() )
            return ResponseEntity.badRequest().build();

        screen.setId(id);

        if(screen.getCinema() != null && screen.getCinema().getId() != null) {
            cinemaService.findById(screen.getCinema().getId()).ifPresent(screen::setCinema);
        }

        Screen updatedScreen = this.screenService.update(screen);

        return ResponseEntity.ok(updatedScreen);
    }


}
