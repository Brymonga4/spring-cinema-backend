package org.example.service;

import org.example.model.Movie;
import org.example.model.Seat;

import java.util.List;

public interface SeatService {

    // CREATE

    Seat save(Seat seat);

    List<Seat> saveAll(List<Seat> seats);

}
