package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.Ticket;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM User s WHERE s.id = :id")
    void findAndLockById(@Param("id") Long id);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);



    Optional<User> findByNickname(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT t.* FROM tickets AS t INNER JOIN bookings AS b ON t.booking_id = b.booking_id INNER JOIN users AS u ON b.user_id = u.user_id WHERE u.user_id = :userId",
            nativeQuery = true)
    List<Ticket> findAllTicketsBoughtByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT t FROM Ticket t " +
            "JOIN Booking b ON t.booking.id = b.id "+
            "JOIN User u ON b.user.id = u.id WHERE u.id = :userId")
    List<Ticket> findTickesOfAUser(@Param("userId") Long userId);


    @Query("SELECT u FROM User u WHERE u.nickname = :nickname AND u.recover_code = :recoverCode")
    Optional<User> findByNicknameAndRecoverCode(@Param("nickname") String nickname, @Param("recoverCode") String recoverCode);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.recover_code = :recoverCode")
    Optional<User> findByEmailAndRecoverCode(@Param("email") String email, @Param("recoverCode") String recoverCode);
}
