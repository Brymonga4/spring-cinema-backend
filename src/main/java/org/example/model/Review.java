package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie", referencedColumnName = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nickname", referencedColumnName = "nickname")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String opinion;

    @Column(nullable = false)
    private Short rating;

    @Column(nullable = false)
    private LocalDateTime date;
}
