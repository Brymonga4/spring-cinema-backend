package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Movie;
import org.example.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.idSeat = :id")
    void findAndLockById(Long id);

    boolean existsByScreenRowsIdAndSeatNumber(Long rowId, Long seatNumber);

    long countByScreenRowsId(Long id);

}
