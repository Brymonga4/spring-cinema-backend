package org.example.controller;

import jakarta.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.example.util.MovieTitleUtil.getFileExtension;
import static org.example.util.MovieTitleUtil.sanitizeTitle;


@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieService service;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public MovieController(MovieService service) {
        this.service = service;
    }

    /*
    GET http://localhost:8080/api/movies
     */

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> findAll(){

        List <MovieDTO> moviesDTO = this.service.findAll();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);

    }

    /*
    GET http://localhost:8080/api/movies/1
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id){

        return this.service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

      /*
    POST http://localhost:8080/api/movies
     */
 /*
    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> create(@Valid @RequestBody Movie movie){

        if(movie.getId() != null)
            return ResponseEntity.badRequest().build();

        MovieDTO savedMovie = this.service.save(movie);

        return ResponseEntity.ok(savedMovie);
    }
    */


    @PostMapping(value = "/movies", consumes = "multipart/form-data")
    public ResponseEntity<MovieDTO> create(@Valid @RequestPart("movie") Movie movie,
                                           @RequestPart("file") MultipartFile file) {
        if (movie.getId() != null)
            return ResponseEntity.badRequest().build();

        return handleFileUploadAndSaveMovie(movie, file);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping(value = "/movies/{id}", consumes = "multipart/form-data")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id,
                                           @Valid @RequestPart("movie") Movie movie,
                                           @RequestPart("file") MultipartFile file) {

        if (service.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();

        movie.setId(id);
        return handleFileUploadAndSaveMovie(movie, file);
    }

    @DeleteMapping ("/movies/{identifier}")
    public ResponseEntity<Movie> deleteById(@PathVariable("identifier") Long id){

        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /*
    OTROS

     */
    @GetMapping ("/movies/releases")
    public ResponseEntity<List<MovieDTO>> findRecentMovies(){

        List <MovieDTO> moviesDTO = this.service.findRecentMovies();

        if (moviesDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(moviesDTO);
    }

    private ResponseEntity<MovieDTO> handleFileUploadAndSaveMovie(Movie movie, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(500).body(null);
                }
            }

            if (Objects.equals(file.getOriginalFilename(), "")) {
                throw new RuntimeException("Título de archivo vacío");
            }

            String sanitizedTitle = sanitizeTitle(movie.getTitle());
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = sanitizedTitle + fileExtension;

            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Thumbnails.of(file.getInputStream())
                    .size(386, 546)
                    .toFile(filePath.toFile());

            movie.setImage(fileName);

            MovieDTO savedMovie = this.service.save(movie);
            return ResponseEntity.ok(savedMovie);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }




}
