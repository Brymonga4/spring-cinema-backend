package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.RowsWithSeatsDTO;
import org.example.dto.ScreenWithSeatsDTO;
import org.example.dto.SeatDTO;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.repository.Screen_rowsRepository;
import org.example.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Screen_rowsServiceImpl implements Screen_rowsService{
    private final Screen_rowsRepository repository;

    private final SeatRepository seatRepository;

    public Screen_rowsServiceImpl (Screen_rowsRepository repository, SeatRepository seatRepository){
        this.repository = repository;
        this.seatRepository = seatRepository;
    }
    @Override
    public List<ScreenRows> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<ScreenRows> findById(Long id) {
        return this.repository.findById(id);
    }



    @Override
    @Transactional
    public ScreenRows save(ScreenRows screenRows) {
        return this.repository.save(screenRows);
    }

    @Override
    public ScreenWithSeatsDTO createSeatsOfScreenRow(ScreenRows sr){

        List<SeatDTO> newSeatsInScreen = new ArrayList<>();
        for (int i = 0;sr.getNumberOfSeats() > i; i++){
           Seat seat = new Seat(
                               null,sr,
                   (long) i+1, "W",false
                               );
            System.out.println(seat);
            Seat seatCreated = this.seatRepository.save(seat);
            System.out.println(seatCreated);
           newSeatsInScreen.add(seatCreated.toDTOWithRowNumber(sr.getRowNumber()));
       }

        return new ScreenWithSeatsDTO(sr.getScreen().getId(), newSeatsInScreen);

    }

    @Override
    public RowsWithSeatsDTO createSeatsOfRow(ScreenRows sr){

        List<SeatDTO> newSeatsInScreen = new ArrayList<>();
        for (int i = 0;sr.getNumberOfSeats() > i; i++){
            Seat seat = new Seat(
                    null,sr,
                    (long) i+1, "N",false
            );
            System.out.println(seat);
            Seat seatCreated = this.seatRepository.save(seat);
            System.out.println(seatCreated);
            newSeatsInScreen.add(seatCreated.toDTOWithRowNumber(sr.getRowNumber()));
        }

        return new RowsWithSeatsDTO(sr.getId(),sr.getRowNumber(), newSeatsInScreen);

    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }
    @Override
    public List<ScreenRows> findAllByScreenId(Long id){
        return this.repository.findAllByScreenId(id);
    }


    @Override
    public ScreenRows update(ScreenRows screenRows) {
        this.repository.findAndLockById(screenRows.getId());
        return this.save(screenRows);
    }
}
