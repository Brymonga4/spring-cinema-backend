package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private Long id;
    private String title;
    private String origTitle;
    private LocalDate release;
    private String genres;
    private String actors;
    private String directors;
    private String script;
    private String producers;
    private String synopsis;
    private boolean originalVersion;
    private boolean spanishVersion;
    private String image;
    private String trailer;
    private String ageRating;
    private Integer duration;
    private String url;
}