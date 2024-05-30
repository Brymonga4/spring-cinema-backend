package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.MovieDTO;
import org.example.dto.TicketDTO;
import org.example.model.Booking;
import org.example.model.Screening;
import org.example.model.Seat;
import org.example.model.Ticket;
import org.example.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private  final SeatRepository seatRepository;
    private  final ScreeningRepository screeningRepository;
    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return this.ticketRepository.findById(id);
    }

    @Override
    public Ticket save(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> saveAll(List<Ticket> tickets) {
        return this.ticketRepository.saveAll(tickets);
    }

    @Override
    public void deleteById(Long id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Ticket update(Ticket ticket) {
        this.ticketRepository.findAndLockById(ticket.getId());
        return this.ticketRepository.save(ticket);
    }

    @Override
    public TicketDTO convertToDto(Ticket ticket) {
        return new TicketDTO(
                ticket.getSeat().getIdSeat(),
                ticket.getScreening().getId()
        );
    }

    @Override
    public Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Seat seat = seatRepository.findById(ticketDTO.getSeat_id())
                .orElseThrow(() -> new EntityNotFoundException("No hay asiento con id : " + ticketDTO.getSeat_id()));
        Screening screening = screeningRepository.findById(ticketDTO.getScreening_id())
                .orElseThrow(() -> new EntityNotFoundException("No hay función con id : " + ticketDTO.getScreening_id()));

        if(screeningRepository.countFromNowToNext7Days(screening.getId())>0) {
            ticket.setScreening(screening);
        }else{
            throw new  RuntimeException("La función ya ha empezado");
        }

        ticket.setSeat(seat);
        ticket.setScreening(screening);
        return ticket;
    }
}
