package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data  // Esta anotación incluye @ToString, @EqualsAndHashCode, @Getter en todos los campos, @Setter en todos los campos no finales, y @RequiredArgsConstructor
@NoArgsConstructor  // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con un argumento para cada campo en la clase
@Builder // Permite Generar constructores dinámicos con solo las propiedades que necesites
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @NotBlank(message = "El titulo no puede estar vacío.")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres.")
    @Column(nullable = false)
    private String title;

    @Column(name = "orig_title", nullable = false)
    private String origTitle;

    @Column(name = "release_date", nullable = false)
    private LocalDate release;

    @Column(nullable = false)
    private String genres;

    @Column(nullable = false)
    private String actors;

    @Column(nullable = false)
    private String directors;

    @Column(nullable = false)
    private String script;

    @Column(nullable = false)
    private String producers;

    @Column(nullable = false)
    private String synopsis;

    @Column(name = "original_version", nullable = false)
    private boolean originalVersion;

    @Column(name = "spanish_version", nullable = false)
    private boolean spanishVersion;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String trailer;

    @Column(name = "age_rating", nullable = false)
    private String ageRating;

    @Column(nullable = false)
    private Integer duration;

}
