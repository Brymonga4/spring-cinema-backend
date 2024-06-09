package org.example.mapper;

import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class MovieMapper {
    public static Movie toEntity(MovieDTO movieDTO) {

       return Movie.builder()
               .id(movieDTO.getId())
               .title(movieDTO.getTitle())
               .origTitle(movieDTO.getOrigTitle())
               .release(movieDTO.getRelease())
               .genres(movieDTO.getGenres())
               .actors(movieDTO.getActors())
               .directors(movieDTO.getDirectors())
               .script(movieDTO.getScript())
               .producers(movieDTO.getProducers())
               .synopsis(movieDTO.getSynopsis())
               .originalVersion(movieDTO.isOriginalVersion())
               .spanishVersion(movieDTO.isSpanishVersion())
               .image(movieDTO.getImage())
               .trailer(movieDTO.getTrailer())
               .ageRating(movieDTO.getAgeRating())
               .duration(movieDTO.getDuration())
               .build();
    }

    public static MovieDTO toDTO(Movie movie) {

        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/movies/{id}")
                .buildAndExpand(movie.getId())
                .toUriString();

        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .origTitle(movie.getOrigTitle())
                .release(movie.getRelease())
                .genres(movie.getGenres())
                .actors(movie.getActors())
                .directors(movie.getDirectors())
                .script(movie.getScript())
                .producers(movie.getProducers())
                .synopsis(movie.getSynopsis())
                .originalVersion(movie.isOriginalVersion())
                .spanishVersion(movie.isSpanishVersion())
                .image(movie.getImage())
                .trailer(movie.getTrailer())
                .ageRating(movie.getAgeRating())
                .duration(movie.getDuration())
                .url(baseUrl)
                .build();
    }


}
