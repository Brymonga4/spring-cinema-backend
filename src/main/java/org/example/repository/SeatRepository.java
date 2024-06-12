package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Movie;
import org.example.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.idSeat = :id")
    void findAndLockById(Long id);

    boolean existsByScreenRowsIdAndSeatNumber(Long rowId, Long seatNumber);

    @Query("SELECT s FROM Seat s WHERE s.screenRows.screen.id = :screenId")
    List<Seat> findAllByScreenId(Long screenId);

    long countByScreenRowsId(Long id);

    @Query(value = "SELECT s.* " +
            "FROM seats s " +
            "JOIN screen_rows sr ON sr.row_id = s.row_id " +
            "JOIN screens sc ON sc.id_screen = sr.screen_id " +
            "JOIN screenings scr ON scr.screen_id = sc.id_screen " +
            "LEFT JOIN tickets t ON t.seat_id = s.seat_id AND t.screening_id = scr.screening_id " +
            "WHERE scr.screening_id = :screeningId " +
            "AND t.ticket_id IS NULL",
            nativeQuery = true)
    List<Seat> findAvailableSeatsByScreeningId(@Param("screeningId") Long screeningId);

    @Query(value = "SELECT s.* " +
            "FROM seats s " +
            "JOIN screen_rows sr ON sr.row_id = s.row_id " +
            "JOIN screens sc ON sc.id_screen = sr.screen_id " +
            "JOIN screenings scr ON scr.screen_id = sc.id_screen " +
            "JOIN tickets t ON t.seat_id = s.seat_id AND t.screening_id = scr.screening_id " +
            "WHERE scr.screening_id = :screeningId",
            nativeQuery = true)
    List<Seat> findUnavailableSeatsByScreeningId(@Param("screeningId") Long screeningId);
}
