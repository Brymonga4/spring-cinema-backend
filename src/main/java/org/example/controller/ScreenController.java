package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.model.*;
import org.example.repository.SeatRepository;
import org.example.service.CinemaService;
import org.example.service.ScreenService;
import org.example.service.Screen_rowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScreenController {
    private final ScreenService screenService;


    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/screens")
    public ResponseEntity<List<ScreenAndSeatsDTO>> findAll(){

        List <ScreenAndSeatsDTO> screenAndSeatsDTOS = this.screenService.findAllScreensWithCinemaAndSeats();

        if (screenAndSeatsDTOS.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenAndSeatsDTOS);

    }

    @GetMapping("/screens/{id}")
    public ResponseEntity<Screen> findById(@PathVariable Long id){

        return this.screenService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }



    @PostMapping("/screens")
    public ResponseEntity<Screen> create(@Valid @RequestBody Screen screen){
        return ResponseEntity.ok(this.screenService.save(screen));
    }

    @PostMapping("/newScreenWithSeats")
    public ResponseEntity<ScreenWithRowsAndSeatsDTO> newScreenWithSeats(@Valid @RequestBody NewScreenDTO newScreenDTO){

        ScreenWithRowsAndSeatsDTO screenWithRowsAndSeatsDTO = this.screenService.newScreenWithSeats(newScreenDTO);

        return ResponseEntity.ok(screenWithRowsAndSeatsDTO);
    }



    @PutMapping("/screens/{id}")
    public ResponseEntity<Screen> update(@PathVariable Long id, @Valid @RequestBody Screen screen){

        if(this.screenService.findById(id).isEmpty() )
            return ResponseEntity.badRequest().build();

        screen.setId(id);

        return ResponseEntity.ok(this.screenService.update(screen));
    }


}
