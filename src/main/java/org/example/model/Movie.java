package org.example.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name = "orig_title", nullable = false)
    private String originalTitle;
    @Column(nullable = false)
    private LocalDate release;

    @Column(nullable = false)
    private String genres;
    @Column(nullable = false)
    private String actors;

    @Column( nullable = false)
    private String directors;

    @Column(nullable = false)
    private String script;

    @Column( nullable = false)
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

    public Movie() {
    }

    public Movie(Long id, String title, String originalTitle, LocalDate release, String genres, String actors, String directors, String script, String producers, String synopsis, boolean originalVersion, boolean spanishVersion, String image, String trailer, String ageRating, Integer duration) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.release = release;
        this.genres = genres;
        this.actors = actors;
        this.directors = directors;
        this.script = script;
        this.producers = producers;
        this.synopsis = synopsis;
        this.originalVersion = originalVersion;
        this.spanishVersion = spanishVersion;
        this.image = image;
        this.trailer = trailer;
        this.ageRating = ageRating;
        this.duration = duration;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean isOriginalVersion() {
        return originalVersion;
    }

    public void setOriginalVersion(boolean originalVersion) {
        this.originalVersion = originalVersion;
    }

    public boolean isSpanishVersion() {
        return spanishVersion;
    }

    public void setSpanishVersion(boolean spanishVersion) {
        this.spanishVersion = spanishVersion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", release=" + release +
                ", genres='" + genres + '\'' +
                ", actors='" + actors + '\'' +
                ", directors='" + directors + '\'' +
                ", script='" + script + '\'' +
                ", producers='" + producers + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", originalVersion=" + originalVersion +
                ", spanishVersion=" + spanishVersion +
                ", image='" + image + '\'' +
                ", trailer='" + trailer + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", duration=" + duration +
                '}';
    }
}
