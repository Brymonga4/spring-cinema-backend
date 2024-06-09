package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.ScreeningDTO;
import org.example.dto.ScreeningDayAndHourDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(name = "start_time")
    private LocalDateTime start_time;

    @Column(nullable = false)
    private String audio;

    @Column(nullable = false)
    private Double price;

    public LocalDateTime  getEndTime() {
        if (movie != null && movie.getDuration() != null) {

            return start_time.plusMinutes(movie.getDuration());
        }
        return start_time;
    }

    public ScreeningDTO toScreeningDTO(){
        return ScreeningDTO.builder()
                .Id(id)
                .cinemaName("Cinema")
                .screen(Math.toIntExact(screen.getId()))
                .movieTitle(movie.getTitle())
                .screeningDayAndHourDTO( ScreeningDayAndHourDTO.builder()
                        .screeningStartTime(this.getTimeFromStarTime())
                        .screeningDay(this.getDayFromStartTime())
                        .build())
                .audio(audio)
                .screeningPrice(price)
                .build();
    }

    public String getDayFromStartTime() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return this.start_time.format(dateFormatter);
    }

    public String getTimeFromStarTime() {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return this.start_time.format(timeFormatter);
    }

}
