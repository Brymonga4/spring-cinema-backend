package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    void findAndLockById(Long id);

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "INNER JOIN Booking b ON t.booking.id = b.id " +
            "INNER JOIN Screening s ON t.screening.id = s.id " +
            "WHERE b.user.id = :userId " +
            "AND s.movie.id = :movieId")
    int countTicketsByUserAndMovie(long userId, long movieId);

    @Query("SELECT t FROM Ticket t WHERE t.booking.id = :bookingId AND t.available = true")
    List<Ticket> findTicketsByBookingIdAndAvailableIsTrue(String bookingId);

}
