package org.example.mapper;

import org.example.dto.CinemaDTO;
import org.example.model.Cinema;

public class CinemaMapper {

    public static CinemaDTO toDTO(Cinema cinema){

        return CinemaDTO.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .build();
    }

    public static Cinema toEntity(CinemaDTO cinemaDTO){
        return Cinema.builder()
                .id(cinemaDTO.getId())
                .name(cinemaDTO.getName())
                .build();
    }
}
