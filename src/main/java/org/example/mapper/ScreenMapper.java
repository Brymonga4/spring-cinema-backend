package org.example.mapper;

import org.example.dto.ReviewDTO;
import org.example.dto.ScreenAndSeatsDTO;
import org.example.model.Movie;
import org.example.model.Review;
import org.example.model.Screen;
import org.example.model.User;

public class ScreenMapper {

    public static Screen toEntity(ScreenAndSeatsDTO screenDTO){
        return Screen.builder()
                .id(screenDTO.getId())

                .build();
    }

    public static ScreenAndSeatsDTO toDTO(Screen screen){
        return ScreenAndSeatsDTO.builder()
                .id(screen.getId())
                .cinemaName(screen.getCinema().getName())
                .supports(screen.getSupports())
                .seats(null)
                .build();
    }
}
