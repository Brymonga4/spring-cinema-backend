package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.*;
import org.example.exception.Exceptions;
import org.example.mapper.ScreenMapper;
import org.example.mapper.ScreeningMapper;
import org.example.mapper.SeatMapper;
import org.example.model.Cinema;
import org.example.model.Screen;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.repository.CinemaRepository;
import org.example.repository.ScreenRepository;
import org.example.repository.Screen_rowsRepository;
import org.example.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final CinemaRepository cinemaRepository;
    private final Screen_rowsRepository screenRowsRepository;
    private final SeatRepository seatRepository;
    public ScreenServiceImpl (ScreenRepository repository, CinemaRepository cinemaRepository, Screen_rowsRepository screenRowsRepository, SeatRepository seatRepository){
        this.screenRepository = repository;
        this.cinemaRepository = cinemaRepository;
        this.screenRowsRepository = screenRowsRepository;
        this.seatRepository = seatRepository;
    }
    @Override
    public List<Screen> findAll() {
        return this.screenRepository.findAll();
    }

    @Override
    public List<ScreenAndSeatsDTO> findAllScreensWithCinemaAndSeats() {
        // Todas las salas
        List <Screen> screens = this.screenRepository.findAll();
        List <ScreenAndSeatsDTO> screenAndSeatsDTOS = new ArrayList<>();
        //Por cada sala
        for(Screen sc: screens){
            // Encuentro su respectivo cine
            Cinema cinema = this.cinemaRepository.findById(sc.getCinema().getId())
                    .orElseThrow(()-> new Exceptions.CinemaNotFoundException(sc.getCinema().getId().toString()));
            sc.setCinema(cinema);
            // Convierto la sala a DTO
            ScreenAndSeatsDTO screenAndSeatsDTO = ScreenMapper.toDTO(sc);
            // Lista auxiliar de asientos DTO para esa sala
            List < SeatDTO> seatsDTO = new ArrayList<>();

            // Encuentro todos los asientos de esa sala
            List <Seat> seats = this.seatRepository.findAllByScreenId(sc.getId());
            // Por cada asiento
            for (Seat s: seats ){
                // Encuentro las filas del asiento
                ScreenRows sr = this.screenRowsRepository.findById(s.getScreenRows().getId())
                        .orElseThrow(()-> new Exceptions.RowNotFoundException(s.getScreenRows().getId().toString()));
                s.setScreenRows(sr);
                // Añado los asientos a la lista auxiliar
                seatsDTO.add(SeatMapper.toDTO(s));
            }
            // Asigno la lista de asientos al DTO
            screenAndSeatsDTO.setSeats(seatsDTO);
        }
        //  Fin de por cada sala


        return screenAndSeatsDTOS;
    }

    @Override
    public Optional<Screen> findById(Long id) {
        return this.screenRepository.findById(id);
    }

    @Override
    public Screen save(Screen screen) {

        if(screen.getCinema().getId() != null) {
            Cinema cinema = cinemaRepository.findById(screen.getCinema().getId())
                    .orElseThrow(() -> new Exceptions.CinemaNotFoundException(screen.getCinema().getId().toString()));
            screen.setCinema(cinema);
        }

        return this.screenRepository.save(screen);

    }

    @Override
    public void deleteById(Long id) {
        this.screenRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Screen update(Screen screen) {

        if(screen.getCinema() != null && screen.getCinema().getId() != null) {
            cinemaRepository.findById(screen.getCinema().getId()).ifPresent(screen::setCinema);
        }

        this.screenRepository.findAndLockById(screen.getId());

        return this.save(screen);

    }

    @Override
    @Transactional
    public ScreenWithRowsAndSeatsDTO newScreenWithSeats(NewScreenDTO newScreenDTO) {
        Screen screen = new Screen();
        if(newScreenDTO.getCinema_id()!= null){
            Cinema cinema = cinemaRepository.findById(newScreenDTO.getCinema_id())
                    .orElseThrow(() -> new Exceptions.CinemaNotFoundException(screen.getCinema().getId().toString()));
            screen.setCinema(cinema); screen.setSupports(newScreenDTO.getSupports());
        }
        List<ScreenRows> screenRowsCreated = new ArrayList<>();

        //Creamos la pantalla
        Screen savedScreen = this.screenRepository.save(screen);
        //Vamos creando las screen_rows ( filas )
        for (int i = 0;newScreenDTO.getRows() > i; i++) {

            ScreenRows screenRows = new ScreenRows();
            screenRows.setScreen(savedScreen);
            screenRows.setRowNumber(i+1);
            screenRows.setNumberOfSeats(newScreenDTO.getNumberOfSeats());

            screenRowsCreated.add(this.screenRowsRepository.save(screenRows));

        }

        List <RowsWithSeatsDTO> rowsWithSeatsDTOS = new ArrayList<>();
        // Creamos todos los asientos por cada fila creada
        for (ScreenRows sr: screenRowsCreated ){

            rowsWithSeatsDTOS.add(this.createSeatsOfRow(sr));

        }

        return new ScreenWithRowsAndSeatsDTO(screen.getId(),rowsWithSeatsDTOS );

    }
    @Transactional
    public RowsWithSeatsDTO createSeatsOfRow(ScreenRows sr){

        List<SeatDTO> newSeatsInScreen = new ArrayList<>();
        for (int i = 0;sr.getNumberOfSeats() > i; i++){

            Seat seat = Seat.builder()
                    .idSeat(null)
                    .screenRows(sr)
                    .seatNumber((long) i+1)
                    .seatType("N")
                    .reserved(false)
                    .build();

            Seat seatCreated = this.seatRepository.save(seat);
            newSeatsInScreen.add(SeatMapper.toDTO(seatCreated));

        }

        return RowsWithSeatsDTO.builder()
                .row_id(sr.getId())
                .row_num(sr.getRowNumber())
                .seats(newSeatsInScreen)
                .build();
    }

}
