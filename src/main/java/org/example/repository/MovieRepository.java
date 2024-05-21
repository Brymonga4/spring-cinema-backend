package org.example.repository;

import org.example.model.Manufacturer;
import org.example.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitleAndOrigTitle(String title, String origTitle);
}
