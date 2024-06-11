package org.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate date = LocalDate.parse(screeningDay, dateFormatter);
            LocalTime time = LocalTime.parse(screeningStartTime, timeFormatter);

            return LocalDateTime.of(date, time);
        }catch (DateTimeParseException e){
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate date = LocalDate.parse(screeningDay, dateFormatter);
                LocalTime time = LocalTime.parse(screeningStartTime, timeFormatter);

                return LocalDateTime.of(date, time);
            } catch (DateTimeParseException ex){
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
