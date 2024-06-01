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
    private final CinemaService cinemaService;

    private final Screen_rowsService screenRowsService;



    public ScreenController(ScreenService screenService, CinemaService cinemaService, Screen_rowsService screenRowsService) {
        this.screenService = screenService;
        this.cinemaService = cinemaService;
        this.screenRowsService = screenRowsService;
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

    @PostMapping("/newScreenWithSeats")
    public ResponseEntity<ScreenWithRowsAndSeatsDTO> newScreenWithSeats(@Valid @RequestBody NewScreenDTO newScreenDTO){
        Screen screen = new Screen();
        if(newScreenDTO.getCinema_id()!= null){
            Cinema cinema = cinemaService.findById(newScreenDTO.getCinema_id())
                    .orElseThrow(() -> new RuntimeException("No se encontró el cine"));
            screen.setCinema(cinema); screen.setSupports(newScreenDTO.getSupports());
        }

        List<ScreenRows> screenRowsCreated = new ArrayList<>();

        //Creamos la pantalla
        Screen savedScreen = this.screenService.save(screen);
        //Vamos creando las screen_rows ( filas )
        for (int i = 0;newScreenDTO.getRows() > i; i++) {

            ScreenRows screenRows = new ScreenRows();
            screenRows.setScreen(savedScreen);
            screenRows.setRowNumber(i+1);
            screenRows.setNumberOfSeats(newScreenDTO.getNumberOfSeats());

            screenRowsCreated.add(this.screenRowsService.save(screenRows));

        }

        List <RowsWithSeatsDTO> rowsWithSeatsDTOS = new ArrayList<>();
        // Creamos todos los asientos por cada fila creada
        for (ScreenRows sr: screenRowsCreated ){

            rowsWithSeatsDTOS.add(this.screenRowsService.createSeatsOfRow(sr));

        }

        ScreenWithRowsAndSeatsDTO screenWithRowsAndSeatsDTO = new ScreenWithRowsAndSeatsDTO(screen.getId(),rowsWithSeatsDTOS );


        return ResponseEntity.ok(screenWithRowsAndSeatsDTO);
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
