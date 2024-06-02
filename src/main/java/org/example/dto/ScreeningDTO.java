package org.example.dto;

import lombok.*;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScreeningDTO {

    private Long Id;

    private String cinemaName;

    private int screen;

    private String movieTitle;
    private ScreeningDayAndHourDTO screeningDayAndHourDTO;

    private String audio;
    private double screenPrice;

    public Screening toEntity(){
        return Screening.builder()

                .movie(Movie.builder()
                        .title(movieTitle)
                        .build())

                .screen(Screen.builder()
                        .id((long) screen)
                        .build())

                .start_time(screeningDayAndHourDTO.converToLocalDateTime())
                .audio(audio)
                .price(screenPrice)
                .build();
    }

}
