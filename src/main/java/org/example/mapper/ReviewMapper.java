package org.example.mapper;

import org.example.dto.ReviewDTO;
import org.example.model.Movie;
import org.example.model.Review;
import org.example.model.User;


public class ReviewMapper {

    public static Review toEntity(ReviewDTO reviewDTO){
        return Review.builder()
                .id(reviewDTO.getId())
                .movie(Movie.builder()
                        .id(0L)
                        .title(reviewDTO.getMovieTitle()).build())
                .user(User.builder()
                        .nickname(reviewDTO.getUserNickname()).build())
                .title(reviewDTO.getTitle())
                .opinion(reviewDTO.getOpinion())
                .rating(reviewDTO.getRating())
                .review_date(reviewDTO.getReview_date())
                .build();
    }

    public static ReviewDTO toDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .movieTitle(review.getMovie().getTitle())
                .userNickname(review.getUser().getNickname())
                .title(review.getTitle())
                .opinion(review.getOpinion())
                .rating(review.getRating())
                .review_date(review.getReview_date())
                .build();
    }

}
