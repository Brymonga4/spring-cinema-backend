package org.example.repository;

import jakarta.persistence.LockModeType;
import org.example.model.ScreenRows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Screen_rowsRepository extends JpaRepository<ScreenRows, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sr FROM ScreenRows sr WHERE sr.id = :id")
    void findAndLockById(Long id);
}
