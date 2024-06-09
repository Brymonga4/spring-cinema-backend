package org.example.mapper;

import org.example.dto.*;
import org.example.model.Screening;
import org.example.model.Seat;
import org.example.model.Ticket;

public class TicketMapper {

    public static Ticket toEntity(TicketDTO ticketDTO){
        return Ticket.builder()
                .seat(Seat.builder()
                        .idSeat(ticketDTO.getSeat_id())
                        .build())
                .screening(Screening.builder()
                        .id(ticketDTO.getScreening_id())
                        .build())
                .build();
    }

    public static TicketDTO toDTO(Ticket ticket){

        return TicketDTO.builder()
                .seat_id(ticket.getSeat().getIdSeat())
                .screening_id(ticket.getScreening().getId())
                .build();

    }

    public static FullTicketWithDetailsDTO toFullTicketWithDetailsDTO(Ticket t){

       return  FullTicketWithDetailsDTO.builder()
                .identifier(t.getBooking().getId())

                .screeningDTO(ScreeningMapper
                                .toDTO(t.getScreening()))

                .rowAndSeat("F "+t.getSeat().getScreenRows().getRowNumber() +" B "+ t.getSeat().getSeatNumber())
                .totalPrice(t.getSeat().convert()* t.getScreening().getPrice())
                .build();
    }


    public static TicketReceiptDTO toTicketReceiptDTO(Ticket t){

        return  TicketReceiptDTO.builder()
                .id(Long.valueOf(t.getBooking().getId()))
                .screeningDate(t.getScreening().getDayFromStartTime()+", "+t.getScreening().getStart_time())
                .cinemaName(t.getScreening().getScreen().getCinema().getName())
                .movieTitle(t.getScreening().getMovie().getTitle())
                .finalPrice(t.getScreening().getPrice() * t.getSeat().convert())
                .build();
    }

}
