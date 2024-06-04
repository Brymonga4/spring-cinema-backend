package org.example.service;

import org.example.dto.ReviewDTO;
import org.example.dto.TicketDTO;
import org.example.model.Review;
import org.example.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    //FIND
    List<Review> findAll();
    Optional<Review> findById(Long id);
    List<Review> findAllByMovieId(Long id);
    // CREATE
    Review save(Review review);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Review update(Review review);

    ReviewDTO convertToDto(Review review);

    Review convertToEntity(ReviewDTO reviewDTO);

    List<Review> findAllByUserId(Long id);

}
