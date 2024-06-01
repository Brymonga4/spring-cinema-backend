package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


}
