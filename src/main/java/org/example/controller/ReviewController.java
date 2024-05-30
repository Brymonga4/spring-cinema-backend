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
    public ResponseEntity<List<Review>> findAllByMovieId(@PathVariable Long id){

        List <Review> reviews = this.reviewService.findAllByMovieId(id);

        if (reviews.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviews);

    }

    @PostMapping("/movie/{id}/reviews")
    public ResponseEntity<Review> create(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Review review = reviewService.convertToEntity(reviewDTO);

        User user = userService.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        review.setUser(user);

        MovieDTO movieDTO = movieService.findById(id).
                orElseThrow(() -> new RuntimeException("No se encontró la película "));
        review.setMovie(movieService.convertToEntity(movieDTO));


        Review savedReview= this.reviewService.save(review);
        return ResponseEntity.ok(savedReview);
    }



}
