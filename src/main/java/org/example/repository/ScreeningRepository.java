package org.example.repository;

import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findAllByMovie_Id(Long movie_id);

    List<Screening> findAllByScreen_Id(Long screenId);


    }
