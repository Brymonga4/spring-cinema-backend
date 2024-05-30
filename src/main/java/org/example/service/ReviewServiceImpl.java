package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ReviewDTO;
import org.example.dto.TicketDTO;
import org.example.model.Movie;
import org.example.model.Review;
import org.example.model.User;
import org.example.repository.MovieRepository;
import org.example.repository.ReviewRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository, UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<Review> findAll() {
      return this.reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return this.reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllByMovieId(Long id) {
        return this.reviewRepository.findAllByMovieId(id);
    }

    @Override
    public Review save(Review review) {
       return this.reviewRepository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        this.reviewRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Review update(Review review) {
       this.reviewRepository.findAndLockById(review.getId());
       return this.reviewRepository.save(review);
    }

    @Override
    public ReviewDTO convertToDto(Review review) {
        return new ReviewDTO(
            review.getUser().getId(),
            review.getTitle(),
            review.getOpinion(),
            review.getRating(),
            review.getReview_date()
        );
    }

    @Override
    public Review convertToEntity(ReviewDTO reviewDTO) {
        return new Review(null, null,
                null,
                reviewDTO.getTitle(),
                reviewDTO.getOpinion(),
                reviewDTO.getRating(),
                reviewDTO.getReview_date());
    }


}
