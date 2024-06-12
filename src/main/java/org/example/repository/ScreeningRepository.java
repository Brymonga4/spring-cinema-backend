package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.dto.MovieDTO;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findAllByMovie_Id(Long movie_id);

    List<Screening> findAllByScreen_Id(Long screenId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Screening s WHERE s.id = :id")
    void findAndLockById(Long id);

    @Query(value = "SELECT * FROM screenings WHERE DATE(start_time) = CURRENT_DATE", nativeQuery = true)
    List<Screening> findScreeningsByToday();


    @Query(value = "SELECT * FROM screenings WHERE start_time > CURRENT_TIMESTAMP AND DATE(start_time) = CURRENT_DATE", nativeQuery = true)
    List<Screening> findScreeningsTodayAfterCurrentTimeStamp();


    @Query(value = "SELECT * FROM screenings WHERE screen_id = :screenId AND DATE(start_time) = CURRENT_DATE ", nativeQuery = true)
    List<Screening> findAllByScreenIdAndToday(@Param("screenId") Long screenId);

    @Query(value = "SELECT * FROM screenings WHERE movie_id = :movieId AND DATE(start_time) = CURRENT_DATE", nativeQuery = true)
    List<Screening> findAllByMovieIdAndToday(@Param("movieId") Long movieId);

    @Query(value = "SELECT * FROM screenings WHERE movie_id = :movieId AND DATE(start_time) BETWEEN CURRENT_DATE AND (CURRENT_DATE + INTERVAL '7 days')", nativeQuery = true)
    List<Screening> findAllByMovieIdAndNext7Days(@Param("movieId") Long movieId);


    @Query(value = "SELECT * FROM screenings WHERE start_time BETWEEN CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP + INTERVAL '7 days')", nativeQuery = true)
    List<Screening> findFromNowToNext7Days();

    @Query(value = "SELECT COUNT(*) FROM screenings WHERE screening_id = :id  AND start_time BETWEEN CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP + INTERVAL '7 days')", nativeQuery = true)
    int countFromNowToNext7Days(Long id);

    }
