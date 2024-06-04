package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.dto.ReviewDTO;
import org.example.model.*;
import org.example.service.MovieService;
import org.example.service.ReviewService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final UserService userService;


    public ReviewController(MovieService movieService, ReviewService reviewService, UserService userService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.userService = userService;
    }


    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> findAll(){

        List <Review> reviews = this.reviewService.findAll();

        if (reviews.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviews);

    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> findById(@PathVariable Long id){

        return this.reviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/movie/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAllByMovieId(@PathVariable Long id){

        List <Review> reviews = this.reviewService.findAllByMovieId(id);

        MovieDTO movieDTO = movieService.findById(id).
                orElseThrow(() -> new RuntimeException("No se encontró la película "));

        for(Review r: reviews){
            r.setMovie(movieService.convertToEntity(movieDTO));
        }

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(Review::reviewToDTO)
                .toList();

        if (reviews.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviewDTOs);

    }

    @GetMapping("/user/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAllByUserId(@PathVariable Long id){

        List <Review> reviews = this.reviewService.findAllByUserId(id);

        MovieDTO movieDTO = movieService.findById(id).
                orElseThrow(() -> new RuntimeException("No se encontró la película "));

        for(Review r: reviews){
            r.setMovie(movieService.convertToEntity(movieDTO));
        }

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(Review::reviewToDTO)
                .toList();

        if (reviews.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviewDTOs);

    }


    @PostMapping("/movie/{id}/reviews")
    public ResponseEntity<ReviewDTO> create(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO){


        Review review = reviewService.convertToEntity(reviewDTO);

        User user = userService.findByNickname(reviewDTO.getUserNickname())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        review.setUser(user);

        MovieDTO movieDTO = movieService.findById(id).
                orElseThrow(() -> new RuntimeException("No se encontró la película "));
        review.setMovie(movieService.convertToEntity(movieDTO));


        Review savedReview = this.reviewService.save(review);

        return ResponseEntity.ok(review.reviewToDTO());
    }





}
