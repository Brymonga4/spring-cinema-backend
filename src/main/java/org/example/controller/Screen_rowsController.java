package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.NewScreenDTO;
import org.example.dto.ScreenWithSeatsDTO;
import org.example.model.Screen;
import org.example.model.ScreenRows;
import org.example.service.ScreenService;
import org.example.service.Screen_rowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Screen_rowsController {
    private final Screen_rowsService screen_rowService;

    public Screen_rowsController(Screen_rowsService screen_rowService){

        this.screen_rowService = screen_rowService;

    }

    @GetMapping("/screen_rows")
    public ResponseEntity<List<ScreenRows>> findAll(){

        List <ScreenRows> screenRows = this.screen_rowService.findAll();

        if (screenRows.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenRows);

    }

    @GetMapping("/screen_rows/{id}")
    public ResponseEntity<ScreenRows> findById(@PathVariable Long id){

        return this.screen_rowService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/screens/{id}/screen_rows")
    public ResponseEntity<List<ScreenRows>>findAllByScreenId(@PathVariable Long id){
        List <ScreenRows> screenRows = this.screen_rowService.findAllByScreenId(id);
        return ResponseEntity.ok(screenRows);
    }


      /*
    POST http://localhost:8080/api/screen_rows
     */

    @PostMapping("/screen_rows")
    public ResponseEntity<ScreenRows> create(@Valid @RequestBody ScreenRows screenRows){

        if(screenRows.getId() !=null)
            return ResponseEntity.badRequest().build();

        ScreenRows savedScreenRows = this.screen_rowService.save(screenRows);

        return ResponseEntity.ok(savedScreenRows);
    }

    @PostMapping("/screenRows")
    public ResponseEntity<ScreenWithSeatsDTO> createSeatsOfScreenRow(@Valid @RequestBody ScreenRows screenRows){

        if(screenRows.getId() !=null)
            return ResponseEntity.badRequest().build();

        ScreenRows savedScreenRows = this.screen_rowService.save(screenRows);

        return ResponseEntity.ok(this.screen_rowService.createSeatsOfScreenRow(savedScreenRows));
    }


    @PutMapping("/screen_rows/{id}")
    public ResponseEntity<ScreenRows> update(@PathVariable Long id, @Valid @RequestBody ScreenRows screenRows){

        if(this.screen_rowService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();

        screenRows.setId(id);
        ScreenRows updatedScreen_rows = this.screen_rowService.update(screenRows);

        return ResponseEntity.ok(updatedScreen_rows);
    }

    @DeleteMapping ("/screen_rows/{identifier}")
    public ResponseEntity<ScreenRows> deleteById(@PathVariable("identifier") Long id){

        this.screen_rowService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
