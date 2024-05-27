package org.example.service;

import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.model.Screening;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<Screening> findAll();
    Optional<Screening> findById(Long id);

    // CREATE

    Screening save(Screening screening);

    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    Screening update(Screening screening);




}
