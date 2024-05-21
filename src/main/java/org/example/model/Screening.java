package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_screening")
    private Long idScreening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_screen", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String audio;

    @Column(nullable = false)
    private Double price;

}
