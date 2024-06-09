package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.RowsWithSeatsDTO;
import org.example.dto.ScreenWithSeatsDTO;
import org.example.dto.SeatDTO;
import org.example.exception.Exceptions;
import org.example.mapper.SeatMapper;
import org.example.model.Screen;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.repository.ScreenRepository;
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
    private final ScreenRepository screenRepository;

    public Screen_rowsServiceImpl (Screen_rowsRepository repository, SeatRepository seatRepository, ScreenRepository screenRepository){
        this.repository = repository;
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
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
    public ScreenRows save(ScreenRows screenRows) {

        Screen screen = screenRepository.findById(screenRows.getScreen().getId())
                .orElseThrow(() -> new Exceptions.ScreenNotFoundException(screenRows.getScreen().getId().toString()));
        screenRows.setScreen(screen);

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
    @Transactional
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
            newSeatsInScreen.add(SeatMapper.toDTO(seatCreated));

        }

        return RowsWithSeatsDTO.builder()
                .row_id(sr.getId())
                .row_num(sr.getRowNumber())
                .seats(newSeatsInScreen)
                .build();
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
    @Transactional
    public ScreenRows update(ScreenRows screenRows) {
        this.repository.findAndLockById(screenRows.getId());
        return this.save(screenRows);
    }
}
