package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.ReviewDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String opinion;

    @Column(nullable = false)
    private Short rating;

    @Column(nullable = false)
    private LocalDateTime review_date;


    public ReviewDTO reviewToDTO(){
        return ReviewDTO.builder()
                .userNickname(user.getNickname())
                .title(title)
                .opinion(opinion)
                .rating(rating)
                .review_date(review_date)
                .build();
    }

}
