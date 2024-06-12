package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.dto.*;
import org.example.exception.Exceptions;
import org.example.mapper.ScreeningMapper;
import org.example.mapper.TicketMapper;
import org.example.model.*;
import org.example.repository.*;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.example.util.IdGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private  final SeatRepository seatRepository;
    private  final ScreeningRepository screeningRepository;

    private final CinemaRepository cinemaRepository;
    private final ScreenRepository screenRepository;

    private final Screen_rowsRepository screenRowsRepository;
    private final UserRepository userRepository;

    private final PdfService pdfService;
    private  final EmailService emailService;


    private final MovieRepository movieRepository;
    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository, CinemaRepository cinemaRepository, ScreenRepository screenRepository, Screen_rowsRepository screenRowsRepository, UserRepository userRepository, PdfService pdfService, EmailService emailService, MovieRepository movieRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.cinemaRepository = cinemaRepository;
        this.screenRepository = screenRepository;
        this.screenRowsRepository = screenRowsRepository;
        this.userRepository = userRepository;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return this.ticketRepository.findById(id);
    }

    @Override
    public Ticket save(Ticket ticket) {

        if(ticket.getBooking().getId()!=null){
            Booking booking = bookingRepository.findByStrId(ticket.getBooking().getId())
                    .orElseThrow(() -> new Exceptions.BookingNotFoundException(ticket.getBooking().getId()));
        }
        if(ticket.getSeat().getIdSeat()!=null){
            Seat seat = seatRepository.findById(ticket.getSeat().getIdSeat())
                    .orElseThrow(() -> new Exceptions.SeatNotFoundException(ticket.getSeat().getIdSeat().toString()));
        }
        if(ticket.getScreening().getId()!=null){
            Screening screening = screeningRepository.findById(ticket.getScreening().getId())
                    .orElseThrow(() -> new Exceptions.ScreeningNotFoundException(ticket.getScreening().getId().toString()));
        }

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
    @Transactional
    public List<FullTicketWithDetailsDTO> buyTicketNoSecurity(TicketWithUserDTO ticketWithUserDTO){

        User user = this.userRepository.findByNickname(ticketWithUserDTO.getUserIdentifier())
                .orElseGet(() -> this.userRepository.findByEmail(ticketWithUserDTO.getUserIdentifier())
                        .orElseThrow(() -> new Exceptions.UserNotFoundException(ticketWithUserDTO.getUserIdentifier())));
        String emailTo = user.getEmail();

        // Convertimos el DTO a tickets
        System.out.println("Convertimos a entidades los DTOS");
        List <Ticket> tickets = this.convertToEntitiesNoSecure(ticketWithUserDTO);

        for(Ticket t: tickets){
            System.out.println(t);
        }
        // Generamos la reserva y guardamos los tickes en la BBDD
        List<FullTicketWithDetailsDTO> fullTickets = this.generateBookingAndSaveTickets(tickets, user);

        if (fullTickets.isEmpty())
            throw new Exceptions.SeatsNotAvailableException("No hay asientos disponibles ");

        // Generamos el PDF con las entradas y lo enviamos al usuario
        this.generatePDFandSendEmailToUserFromFullTickets(fullTickets, emailTo);

        return fullTickets;
    }

    @Override
    @Transactional
    public List<Ticket> convertToEntitiesNoSecure(TicketWithUserDTO ticketWithUserDTO) {
        List <Ticket> tickets = new ArrayList<>();

        System.out.println (ticketWithUserDTO.getSeats().size());

        for(Long seatId : ticketWithUserDTO.getSeats()){
            System.out.println(seatId);
            Ticket ticket = new Ticket();

            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new Exceptions.SeatNotFoundException(seatId.toString()));
            System.out.println(seat);

            ScreenRows sr = screenRowsRepository.findById(seat.getScreenRows().getId())
                    .orElseThrow(() -> new Exceptions.RowNotFoundException(seat.getScreenRows().getId().toString()));
            seat.setScreenRows(sr);

            System.out.println(sr);

            Screening screening = screeningRepository.findById(ticketWithUserDTO.getScreening_id())
                    .orElseThrow(() -> new Exceptions.ScreeningNotFoundException(ticketWithUserDTO.getScreening_id().toString()));
            Movie movie = movieRepository.findById(screening.getMovie().getId())
                    .orElseThrow(() -> new Exceptions.MovieNotFoundException(screening.getMovie().getId().toString()));
            screening.setMovie(movie);

            System.out.println(screening);
            System.out.println(movie);

            Screen screen = screenRepository.findById(screening.getScreen().getId())
                    .orElseThrow(() -> new Exceptions.ScreenNotFoundException( screening.getScreen().getId().toString()));
            screening.setScreen(screen);

            Cinema cinema = cinemaRepository.findById(screen.getCinema().getId())
                    .orElseThrow(() -> new Exceptions.CinemaNotFoundException(screen.getCinema().getId().toString()));
            screen.setCinema(cinema);

            if(screeningRepository.countFromNowToNext7Days(screening.getId())>0) {
                ticket.setScreening(screening);
            }else{
                throw new Exceptions.ScreeninAlreadyStartedtException(screening.getId().toString());
            }

            ticket.setSeat(seat);
            ticket.setScreening(screening);

            tickets.add(ticket);
        }


        return tickets;
    }

    @Override
    @Transactional
    public List<FullTicketWithDetailsDTO> generateBookingAndSaveTickets(List<Ticket> tickets, User user) {

        List <FullTicketWithDetailsDTO> fullTicketsGenerated = new ArrayList<>();

        Booking booking = new Booking();
        System.out.println("nuevo booking "+ booking);
        booking.setId(IdGenerator.randomString(9));
        booking.setUser(user);
        booking = bookingRepository.save(booking);

        System.out.println("booking con identificador generado"+ booking);

        System.out.println("vamos a recorrer los tickets del dto y crearlos");

        for (Ticket t: tickets){
            t.setBooking(booking);
            System.out.println("identificador de ticket" + t.getBooking().getId());

            FullTicketWithDetailsDTO fullTicket = TicketMapper.toFullTicketWithDetailsDTO(t);
            System.out.println("identificador de fullticket "+fullTicket.getIdentifier());

            fullTicketsGenerated.add(fullTicket);

            Ticket savedSTicket = this.ticketRepository.save(t);
        }

        return fullTicketsGenerated;
    }

    @Override
    @Transactional
    public void generatePDFandSendEmailToUserFromFullTickets(List<FullTicketWithDetailsDTO> fullTickets, String emailTo) {

        try {
            byte[] pdfBytes = pdfService.generatePdfOfFullTicket(fullTickets);

            //System.out.println("identificador que vamos a crear el QR de"+fullTickets.getFirst().getIdentifier());
            try{
                emailService.sendEmailWithPdf(emailTo,
                        "Entrada de Cine - FilMMes",
                        "Muchas gracias por efectuar su compra.",
                        fullTickets.get(0).getIdentifier(),
                        pdfBytes);

            }catch (Exception e){
                throw new Exceptions.EmailErrorException(e.getMessage());
            }
        } catch (Exception e) {
            throw new Exceptions.PDFErrorException(e.getMessage());
        }
    }

    @Override
    public int countTicketsByUserAndMovie(long userId, long movieId) {
        return this.ticketRepository.countTicketsByUserAndMovie(userId, movieId);
    }

    @Override
    public List<Ticket> findTicketsByBookingIdAndAvailableIsTrue(String bookingId) {
        return this.ticketRepository.findTicketsByBookingIdAndAvailableIsTrue(bookingId);
    }




}
