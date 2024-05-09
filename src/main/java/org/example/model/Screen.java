package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_screen")
    private Long idScreen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cinema", nullable = false)
    private Cinema cinema;

    @Column(name = "distribution", nullable = false)
    private String distribution;

    @Column(name = "supports", nullable = false)
    private String supports;

    // Constructor, Getters, and Setters omitted for brevity
}
