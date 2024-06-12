package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.CinemaDTO;
import org.example.mapper.CinemaMapper;
import org.example.model.Cinema;
import org.example.model.Seat;
import org.example.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CinemaController {

    private final CinemaService service;

    public CinemaController(CinemaService service){
        this.service = service;
    }


    @GetMapping("/cinemas")
    public ResponseEntity<List<CinemaDTO>> findAll(){

        List<CinemaDTO> cinemaDTOs = this.service.findAll().stream()
                .map(CinemaMapper::toDTO)
                .toList();

        if (cinemaDTOs.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cinemaDTOs);

    }

    @GetMapping("/cinemas/{id}")
    public ResponseEntity<CinemaDTO> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(cinema -> ResponseEntity.ok(CinemaMapper.toDTO(cinema)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PostMapping("/cinemas")
    public ResponseEntity<CinemaDTO> create(@Valid @RequestBody Cinema cinema){

        if(cinema.getId() != null)
            return ResponseEntity.badRequest().build();

        Cinema savedCinema = this.service.save(cinema);

        return ResponseEntity.ok(CinemaMapper.toDTO(savedCinema));
    }

    @PutMapping("/cinemas/{id}")
    public ResponseEntity<Cinema> update(@PathVariable Long id, @Valid @RequestBody Cinema cinema){

        if(this.service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        cinema.setId(id);
        Cinema updatedCinema = this.service.update(cinema);

        return ResponseEntity.ok(updatedCinema);
    }

    @DeleteMapping ("/cinemas/{id}")
    public ResponseEntity<Seat> deleteById(@PathVariable Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
