package org.example.service;

import org.example.dto.MovieDTO;
import org.example.dto.ScreenAndSeatsDTO;
import org.example.dto.ScreeningDayAndHourDTO;
import org.example.model.Movie;
import org.example.model.Screening;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<Screening> findAll();

    Optional<Screening> findById(Long id);
    int countFromNowToNext7Days(Long id);
    List<Screening> findAllByScreenIdAndToday(Long screenId);
    List<Screening> findScreeningsByToday();
    List<Screening> findAllByMovieIdAndToday(Long movieId);
    List<Screening> findScreeningsTodayAfterCurrentTimeStamp();

    List<Screening> findAllByMovieIdAndNext7Days(Long movieId);

    List<Screening> findFromNowToNext7Days();

    // CREATE

    Screening save(Screening screening);

    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Screening update(Screening screening);


    ScreeningDayAndHourDTO toScreeningDayAndHourDTO(LocalDateTime startTime);


}
