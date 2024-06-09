package org.example.service;

import org.example.dto.MovieDTO;
import org.example.dto.NewScreenDTO;
import org.example.dto.ScreenAndSeatsDTO;
import org.example.dto.ScreenWithRowsAndSeatsDTO;
import org.example.model.Movie;
import org.example.model.Screen;

import java.util.List;
import java.util.Optional;

public interface ScreenService {

    //FIND
    List<Screen> findAll();

    List<ScreenAndSeatsDTO> findAllScreensWithCinemaAndSeats();
    Optional<Screen> findById(Long id);
    // CREATE
    Screen save(Screen screen);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Screen update(Screen screen);

    ScreenWithRowsAndSeatsDTO newScreenWithSeats(NewScreenDTO newScreenDTO);

}


