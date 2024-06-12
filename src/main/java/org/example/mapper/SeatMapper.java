package org.example.mapper;

import org.example.dto.SeatDTO;
import org.example.dto.SeatTypeDTO;
import org.example.model.ScreenRows;
import org.example.model.Seat;

public class SeatMapper {

    public static Seat toEntity(SeatDTO seatDTO){
        return Seat.builder()
                .idSeat(seatDTO.getId())
                .screenRows(ScreenRows.builder()
                        .rowNumber(seatDTO.getRow_number())
                        .build())
                .seatNumber((long) seatDTO.getSeat_number())
                .reserved(false)
                .seatType(String.valueOf(seatDTO.getSeat_type()))
                .build();
    }

    public static Seat toEntity(SeatTypeDTO seatTypeDTO){
        return Seat.builder()
                .idSeat(seatTypeDTO.getId())
                .seatType(String.valueOf(seatTypeDTO.getSeat_type()))
                .build();
    }

    public static SeatDTO toDTO (Seat seat){
        return SeatDTO.builder()
                .id(seat.getIdSeat())
                .row_number(seat.getScreenRows().getRowNumber())
                .seat_number(Math.toIntExact(seat.getSeatNumber()))
                .seat_type(seat.getSeatType().charAt(0))
                .available(seat.getReserved())
                .build();
    }

    public static SeatTypeDTO toSeatTypeDTO (Seat seat){
        return SeatTypeDTO.builder()
                .id(seat.getIdSeat())
                .seat_type(seat.getSeatType().charAt(0))
                .build();
    }
}
