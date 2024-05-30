package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Booking;
import org.example.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    void findAndLockById(String id);
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findByStrId(String id);

}
