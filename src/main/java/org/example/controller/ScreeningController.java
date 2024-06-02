package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.dto.ScreeningDTO;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;
import org.example.service.MovieService;
import org.example.service.ScreenService;
import org.example.service.ScreeningService;
import org.example.util.DateComparison;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScreeningController {

    private ScreeningService screeningService;
    private ScreenService screenService;
    private MovieService movieService;

    public ScreeningController(ScreeningService screeningService,ScreenService screenService,  MovieService movieService){
        this.screeningService = screeningService;
        this.screenService = screenService;
        this.movieService = movieService;
    }

     /*
    GET http://localhost:8080/api/screenings
     */

    @GetMapping("/screenings")
    public ResponseEntity<List<Screening>> findAll(){

        List <Screening> screenings = this.screeningService.findAll();

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }
    @GetMapping("/screenings/today")
    public ResponseEntity<List<Screening>> findAllByToday(){

        List <Screening> screenings = this.screeningService.findScreeningsByToday();

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/screenings/today/available")
    public ResponseEntity<List<Screening>> findScreeningsAfterCurrentTimeStamp(){

        List <Screening> screenings = this.screeningService.findScreeningsTodayAfterCurrentTimeStamp();

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/screen/{id}/screenings/today")
    public ResponseEntity<List<Screening>> findAllByScreenIdAndToday(@PathVariable Long id){

        List <Screening> screenings = this.screeningService.findAllByScreenIdAndToday(id);

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/movie/{id}/screenings/today")
    public ResponseEntity<List<Screening>> findAllByMovieIdAndToday(@PathVariable Long id){

        List <Screening> screenings = this.screeningService.findAllByMovieIdAndToday(id);

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/movie/{id}/screenings/week")
    public ResponseEntity<List<Screening>> findAllByMovieIdAndNext7Days(@PathVariable Long id){

        List <Screening> screenings = this.screeningService.findAllByMovieIdAndNext7Days(id);

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/screenings/week/available")
    public ResponseEntity<List<Screening>> findFromNowToNext7Days(){

        List <Screening> screenings = this.screeningService.findFromNowToNext7Days();

        if (screenings.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenings);

    }

    @GetMapping("/screenings/{id}")
    public ResponseEntity<Screening> findById(@PathVariable Long id){

        return this.screeningService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }





    @PostMapping("/screenings")
    public ResponseEntity<ScreeningDTO> create(@Valid @RequestBody ScreeningDTO screeningDTO){

        Screening screening = screeningDTO.toEntity();

        if(screening.getScreen().getId()!= null){
            Screen screen = screenService.findById(screening.getScreen().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la sala"));
            screening.setScreen(screen);

            //Recuperar cine
        }
        if(screening.getMovie().getTitle()!= null){
            MovieDTO movieDTO = movieService.findMovieByTitle(screening.getMovie().getTitle())
                    .orElseThrow(() -> new RuntimeException("No se encontró la película"));
            screening.setMovie(movieService.convertToEntity(movieDTO));
        }

        System.out.println(ZonedDateTime.now());
        System.out.println(screening.getStart_time());

        if (screening.getStart_time().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now()))) {
            throw new RuntimeException("No se puede crear una función con un tiempo de inicio inferior al actual");
        }

        Screening savedScreening = this.screeningService.save(screening);

        return ResponseEntity.ok(savedScreening.toScreeningDTO());
    }

    /*
      PUT http://localhost:8080/api/movies
       */
    @PutMapping("/screenings/{id}")
    public ResponseEntity<Screening> update(@PathVariable Long id, @Valid @RequestBody Screening screening){


        if(this.screeningService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        screening.setId(id);
        Screening updatedScreening = this.screeningService.update(screening);

        return ResponseEntity.ok(updatedScreening);
    }

    @DeleteMapping ("/screenings/{id}")
    public ResponseEntity<Screening> deleteById(@PathVariable Long id){

        this.screeningService.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
