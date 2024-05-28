package org.example.controller;

import jakarta.validation.Valid;
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
    private Screen_rowsService screen_rowService;
    private ScreenService screenService;

    public Screen_rowsController(Screen_rowsService screen_rowService, ScreenService screenService){

        this.screen_rowService = screen_rowService;
        this.screenService = screenService;
    }
  /*
    GET http://localhost:8080/api/screen_rows
     */

    @GetMapping("/screen_rows")
    public ResponseEntity<List<ScreenRows>> findAll(){

        List <ScreenRows> screenRows = this.screen_rowService.findAll();

        if (screenRows.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(screenRows);

    }

    /*
    GET http://localhost:8080/api/screen_rows/1
     */
    @GetMapping("/screen_rows/{id}")
    public ResponseEntity<ScreenRows> findById(@PathVariable Long id){

        return this.screen_rowService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/screen_rows
     */

    @PostMapping("/screen_rows")
    public ResponseEntity<ScreenRows> create(@Valid @RequestBody ScreenRows screenRows){

        if(screenRows.getScreen().getId()!= null){
            Screen screen = screenService.findById(screenRows.getScreen().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la sala"));
            screenRows.setScreen(screen);
        }


        ScreenRows savedScreenRows = this.screen_rowService.save(screenRows);

        return ResponseEntity.ok(savedScreenRows);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/screen_rows/{id}")
    public ResponseEntity<ScreenRows> update(@PathVariable Long id, @Valid @RequestBody ScreenRows screenRows){

        System.out.println(this.screen_rowService.findById(id).isEmpty());
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
