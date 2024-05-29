package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private SeatRepository repository;

    public SeatServiceImpl (SeatRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Seat> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Seat> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Seat save(Seat seat) {

        boolean exists = this.repository.existsByScreenRowsIdAndSeatNumber(seat.getScreenRows().getId(), seat.getSeatNumber());
        if (exists)
            throw new IllegalStateException("Asiento ya registrado en esta fila.");

        if(!seatsLeftInScreenRow(seat.getScreenRows()))
            throw new IllegalStateException("No se pueden añadir más asientos a esta fila, límite alcanzado.");

        return this.repository.save(seat);

    }

    public boolean seatsLeftInScreenRow(ScreenRows sr){
        long count = this.repository.countByScreenRowsId(sr.getId());

        return count < sr.getNumber_of_seats();
    }

    @Override
    public List<Seat> findAllSeatsInScreen(Long id){
       return  this.repository.findAllByScreenId(id);
    }

    @Override
    @Transactional
    public List<Seat> saveAll(List<Seat> seats) throws IllegalStateException {
        // Mapa para mantener la cuenta de asientos adicionales solicitados por fila
        Map<Long, Integer> additionalSeatsPerRow = new HashMap<>();

        // Recorrer cada asiento y contar cuántos se añaden por fila
        for (Seat seat : seats) {
            additionalSeatsPerRow.put(seat.getScreenRows().getId(), additionalSeatsPerRow.getOrDefault(seat.getScreenRows().getId(), 0) + 1);
        }

        for (Seat seat : seats) {
            Long rowId = seat.getScreenRows().getId();
            ScreenRows screenRow = seat.getScreenRows();

            // Contar los asientos existentes en esta fila
            long existingSeatsCount = this.repository.countByScreenRowsId(rowId);

            // Calcular el total de asientos después de añadir los nuevos
            int totalSeatsAfterAdding = (int) existingSeatsCount + additionalSeatsPerRow.get(rowId);

            // Comprobar si se excede la capacidad de la fila
            if (totalSeatsAfterAdding > screenRow.getNumber_of_seats()) {
                throw new IllegalStateException("La capacidad de la fila " + rowId + " será excedida al añadir estos asientos.");
            }

            // Verificar si el asiento ya existe
            boolean exists = this.repository.existsByScreenRowsIdAndSeatNumber(rowId, seat.getSeatNumber());
            if (exists) {
                throw new IllegalStateException("Asiento ya registrado en la fila con ID: " + rowId);
            }
        }

        // Guardar todos los asientos si todas las validaciones son exitosas
        return this.repository.saveAll(seats);
    }



    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Seat update(Seat seat) {
        this.repository.findAndLockById(seat.getIdSeat());
        return this.repository.save(seat);
    }


}
