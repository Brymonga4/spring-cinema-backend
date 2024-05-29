package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.service.Screen_rowsService;
import org.example.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeatController {

    private SeatService seatService;
    private Screen_rowsService screenRowsService;

    public SeatController( SeatService seatService, Screen_rowsService screenRowsService){

        this.seatService = seatService;
        this.screenRowsService = screenRowsService;
    }


    /*
    GET http://localhost:8080/api/seats
     */

    @GetMapping("/seats")
    public ResponseEntity<List<Seat>> findAll(){

        List <Seat> seats = this.seatService.findAll();

        if (seats.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(seats);

    }


    @GetMapping("screens/{id}/seats")
    public ResponseEntity<List<Seat>> findAllSeatsInScreen(@PathVariable Long id){

        List <Seat> seats = this.seatService.findAllSeatsInScreen(id);

        if (seats.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(seats);

    }

    /*
    GET http://localhost:8080/api/seats/1
     */
    @GetMapping("/seats/{id}")
    public ResponseEntity<Seat> findById(@PathVariable Long id){

        return this.seatService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/movies
     */

    @PostMapping("/seats")
    public ResponseEntity<Seat> create(@Valid @RequestBody Seat seat){

        if(seat.getScreenRows().getId() != null) {
            ScreenRows sr = screenRowsService.findById(seat.getScreenRows().getId())
                    .orElseThrow( () -> new RuntimeException(("No se encontró la fila")));
            seat.setScreenRows(sr);
        }
        Seat savedSeat = this.seatService.save(seat);

        return ResponseEntity.ok(savedSeat);
    }
    @PostMapping("/seats/bulk")
    public ResponseEntity<List<Seat>>createBulk(@Valid @RequestBody List<Seat> seats){

        for (Seat seat : seats) {
            System.out.println(seat);
            if (seat.getScreenRows().getId() != null) {
                ScreenRows sr = screenRowsService.findById(seat.getScreenRows().getId())
                        .orElseThrow(() -> new RuntimeException("No se encontró la fila para el asiento: " + seat.getIdSeat()));
                seat.setScreenRows(sr);
            }
        }
        List<Seat> savedSeats = seatService.saveAll(seats);

        return ResponseEntity.ok(savedSeats);
    }
    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/seats/{id}")
    public ResponseEntity<Seat> update(@PathVariable Long id, @Valid @RequestBody Seat seat){

        System.out.println(this.seatService.findById(id).isEmpty());
        if(this.seatService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        seat.setIdSeat(id);
        Seat updatedSeat = this.seatService.update(seat);

        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping ("/seats/{identifier}")
    public ResponseEntity<Seat> deleteById(@PathVariable("identifier") Long id){

        this.seatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
