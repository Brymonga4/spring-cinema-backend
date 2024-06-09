package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.MovieDTO;
import org.example.dto.ReviewDTO;
import org.example.mapper.ReviewMapper;
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
    private final ReviewService reviewService;



    public ReviewController( ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> findAll(){

        List <ReviewDTO> reviewsDTO = this.reviewService.findAll().stream()
                .map(ReviewMapper::toDTO)
                .toList();

        if (reviewsDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviewsDTO);

    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable Long id){

        return this.reviewService.findById(id)
                .map(review -> ResponseEntity.ok(ReviewMapper.toDTO(review)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/movie/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAllByMovieId(@PathVariable Long id){

        List<ReviewDTO> reviewDTOs = this.reviewService.findAllByMovieId(id);

        if (reviewDTOs.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviewDTOs);

    }

    @GetMapping("/user/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findAllByUserId(@PathVariable Long id){

        List <ReviewDTO> reviewsDTO = this.reviewService.findAllByUserId(id);

        if (reviewsDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(reviewsDTO);

    }


    @PostMapping("/movie/{id}/reviews")
    public ResponseEntity<ReviewDTO> create(@PathVariable Long id, @Valid @RequestBody ReviewDTO reviewDTO){

        Review review = ReviewMapper.toEntity(reviewDTO);
        review.getMovie().setId(id);

        return ResponseEntity.ok(this.reviewService.save(review));
    }





}
