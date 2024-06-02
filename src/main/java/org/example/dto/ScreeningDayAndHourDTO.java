package org.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScreeningDayAndHourDTO {
    private String screeningDay;
    private String screeningStartTime;


    public LocalDateTime converToLocalDateTime(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(screeningDay, dateFormatter);
        LocalTime time = LocalTime.parse(screeningStartTime, timeFormatter);

        return LocalDateTime.of(date, time);
    }

}
