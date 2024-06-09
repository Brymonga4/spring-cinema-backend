package org.example.mapper;

import org.example.dto.ScreeningDTO;
import org.example.dto.ScreeningDayAndHourDTO;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreeningMapper {

    public static Screening toEntity(ScreeningDTO screeningDTO){
        return Screening.builder()

                .movie(Movie.builder()
                        .title(screeningDTO.getMovieTitle())
                        .build())

                .screen(Screen.builder()
                        .id((long) screeningDTO.getScreen())
                        .build())

                .start_time(screeningDTO.getScreeningDayAndHourDTO().converToLocalDateTime())
                .audio(screeningDTO.getAudio())
                .price(screeningDTO.getScreeningPrice())
                .build();
    }

    public static ScreeningDTO toDTO(Screening screening){
        return ScreeningDTO.builder()
                .Id(screening.getId())
                .cinemaName(screening.getScreen().getCinema().getName())
                .screen(Math.toIntExact(screening.getScreen().getId()))
                .movieTitle(screening.getMovie().getTitle())

                .screeningDayAndHourDTO(ScreeningMapper.toScreeningDayAndHourDTO(screening.getStart_time()))

                .audio(screening.getAudio())
                .screeningPrice(screening.getPrice())
                .build();
    }

    public static ScreeningDayAndHourDTO toScreeningDayAndHourDTO(LocalDateTime startTime) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = startTime.toLocalDate().format(dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = startTime.toLocalTime().format(timeFormatter);

        return ScreeningDayAndHourDTO.builder()
                .screeningDay(date)
                .screeningStartTime(time)
                .build();
    }

}
