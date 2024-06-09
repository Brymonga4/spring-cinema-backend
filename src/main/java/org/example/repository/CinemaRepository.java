package org.example.repository;

import org.example.model.Cinema;
import org.example.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Optional<Cinema> findCinemaByName(String name);
}
