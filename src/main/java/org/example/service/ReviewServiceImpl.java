package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.MovieDTO;
import org.example.dto.ReviewDTO;
import org.example.dto.ReviewsAndAverageDTO;
import org.example.dto.TicketDTO;
import org.example.exception.Exceptions;
import org.example.mapper.ReviewMapper;
import org.example.model.Movie;
import org.example.model.Review;
import org.example.model.User;
import org.example.repository.MovieRepository;
import org.example.repository.ReviewRepository;
import org.example.repository.TicketRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository, UserRepository userRepository, TicketRepository ticketRepository){
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
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
    public List<ReviewDTO> findAllByMovieId(Long id) {

        List <Review> reviews = this.reviewRepository.findAllByMovieId(id);

        Movie movie = this.movieRepository.findById(id).
                orElseThrow(() -> new Exceptions.MovieNotFoundException(id.toString()));

        for(Review r: reviews){
            r.setMovie(movie);
        }

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::toDTO)
                .toList();

        return reviewDTOs;

    }

    @Override
    public ReviewsAndAverageDTO findAllByMovieIdAndAverage(Long id) {
        List <Review> reviews = this.reviewRepository.findAllByMovieId(id);

        Movie movie = this.movieRepository.findById(id).
                orElseThrow(() -> new Exceptions.MovieNotFoundException(id.toString()));

        double averageRating = 0;
        for(Review r: reviews){
            averageRating += r.getRating();
            r.setMovie(movie);
        }
        if(!reviews.isEmpty())
            averageRating = averageRating/reviews.size();


        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::toDTO)
                .toList();



        return ReviewsAndAverageDTO.builder()
                .reviews(reviewDTOs)
                .averageRating(averageRating)
                .build();
    }

    @Override
    @Transactional
    public ReviewDTO save(Review review) {

        User user = this.userRepository.findByNickname(review.getUser().getNickname())
                .orElseThrow(() -> new Exceptions.UserNotFoundException(review.getUser().getNickname()));
        review.setUser(user);


        Movie movie =this.movieRepository.findById(review.getMovie().getId()).
                orElseThrow(() -> new Exceptions.MovieNotFoundException(review.getMovie().getId().toString()));

        review.setMovie(movie);

        if(this.ticketRepository.countTicketsByUserAndMovie(user.getId(), movie.getId()) <= 0) {
            throw new Exceptions.UserCantReviewMovieException(movie.getId().toString());
        }

        Review reviewSaved = this.reviewRepository.save(review);
        return ReviewMapper.toDTO(reviewSaved);
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
    public List<ReviewDTO> findAllByUserId(Long id) {

        List <Review> reviews = this.reviewRepository.findAllByUserId(id);

        Movie movie = this.movieRepository.findById(id).
                orElseThrow(() -> new Exceptions.MovieNotFoundException(id.toString()));

        for(Review r: reviews){
            r.setMovie(movie);
        }

        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::toDTO)
                .toList();


        return reviewDTOs;

    }


}
