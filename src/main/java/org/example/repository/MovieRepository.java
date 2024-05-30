package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTitleAndOrigTitle(String title, String origTitle);

    // @Query(value = "SELECT * FROM movies WHERE release_date >= CURRENT_DATE - INTERVAL '14 days'", nativeQuery = true)
    // List<Movie> findRecentMovies();
    @Query("SELECT m FROM Movie m WHERE m.release >= :cutoffDate")
    List<Movie> findRecentMovies(@Param("cutoffDate") LocalDate cutoffDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    void findAndLockById(Long id);

    @Query("SELECT m FROM Movie m WHERE m.id IN (SELECT s.movie.id FROM Screening s WHERE s.start_time BETWEEN CURRENT_DATE AND :endDate)")
    List<Movie> findMovieListingUntilenDate(LocalDateTime endDate);

    Movie findMovieByTitle(String title);




}
