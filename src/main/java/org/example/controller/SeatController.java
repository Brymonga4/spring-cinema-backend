package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.SeatDTO;
import org.example.dto.SeatTypeDTO;
import org.example.mapper.SeatMapper;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.service.Screen_rowsService;
import org.example.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SeatController {

    private final SeatService seatService;

    public SeatController( SeatService seatService){

        this.seatService = seatService;

    }


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

    @GetMapping("screening/{id}/seats")
    public ResponseEntity<List<SeatDTO>> findAllSeatsInScreening(@PathVariable Long id){

        List <SeatDTO> seats = this.seatService.findAllSeatsOfScreeningId(id);

        if (seats.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(seats);

    }

    @GetMapping("/seats/{id}")
    public ResponseEntity<Seat> findById(@PathVariable Long id){

        return this.seatService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PostMapping("/seats")
    public ResponseEntity<Seat> create(@Valid @RequestBody Seat seat){

        Seat savedSeat = this.seatService.save(seat);

        return ResponseEntity.ok(savedSeat);
    }


    @PutMapping("/seats/{id}")
    public ResponseEntity<Seat> update(@PathVariable Long id, @Valid @RequestBody Seat seat){

        System.out.println(this.seatService.findById(id).isEmpty());
        if(this.seatService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        seat.setIdSeat(id);
        Seat updatedSeat = this.seatService.update(seat);

        return ResponseEntity.ok(updatedSeat);
    }

    @PutMapping("/seats/bulk")
    public ResponseEntity<List<Seat>> updateBulk(@Valid @RequestBody List<SeatTypeDTO> seatsTypeDTO){

        List<Seat> seatsUpdated = this.seatService.updateSeatsDTO(seatsTypeDTO);
        return ResponseEntity.ok(seatsUpdated);

    }

    @DeleteMapping ("/seats/{identifier}")
    public ResponseEntity<Seat> deleteById(@PathVariable("identifier") Long id){

        this.seatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
