package org.example.service;

import org.example.model.ScreenRows;

import java.util.List;
import java.util.Optional;

public interface Screen_rowsService {

    //FIND
    List<ScreenRows> findAll();
    Optional<ScreenRows> findById(Long id);

    // CREATE
    ScreenRows save(ScreenRows screenRows);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    ScreenRows update(ScreenRows screenRows);
}
