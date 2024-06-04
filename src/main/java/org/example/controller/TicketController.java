package org.example.controller;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.model.*;
import org.example.service.*;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.example.util.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final BookingService bookingService;
    private final SeatService seatService;
    private final MovieService movieService;

    private final EmailService emailService;

    private final UserService userService;

    private final PdfService pdfService;

    public TicketController(TicketService ticketService, ScreeningService screeningService, BookingService bookingService, SeatService seatService, MovieService movieService, EmailService emailService, UserService userService, PdfService pdfService){
        this.ticketService = ticketService;
        this.screeningService = screeningService;
        this.bookingService = bookingService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.emailService = emailService;
        this.userService = userService;
        this.pdfService = pdfService;
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id){

        return this.ticketService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/ticket")
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket ticket){

        if(ticket.getBooking().getId()!=null){
            Booking booking = bookingService.findByStrId(ticket.getBooking().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la reserva"));
        }
        if(ticket.getSeat().getIdSeat()!=null){
            Seat seat = seatService.findById(ticket.getSeat().getIdSeat())
                    .orElseThrow(() -> new RuntimeException("No se encontró el asiento"));
        }
        if(ticket.getScreening().getId()!=null){
            Screening screening = screeningService.findById(ticket.getScreening().getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la función"));
        }


        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }

    @PostMapping("/screenings/ticket/buy")
    public ResponseEntity<Ticket> buyTicket(@Valid @RequestBody TicketDTO ticketDTO){

        System.out.println(ticketDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ticket ticket = this.ticketService.convertToEntity(ticketDTO);

        if(user.getId()!=null){
            Booking booking = new Booking();
            System.out.println(booking);
            booking.setId(IdGenerator.randomString(9));
            booking.setUser(user);
            booking = bookingService.save(booking);

            ticket.setBooking(booking);
        }

        System.out.println(ticket);

        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }

    @PostMapping("/screenings/ticket/buyNoSecurity")
    public ResponseEntity<List<FullTicketWithDetailsDTO>> buyTicketNoSecurity(@Valid @RequestBody TicketWithUserDTO ticketWithUserDTO){

        System.out.println(ticketWithUserDTO);

        User user = this.userService.findByNickname(ticketWithUserDTO.getUserIdentifier())
                .orElseGet(() -> this.userService.findByEmail(ticketWithUserDTO.getUserIdentifier())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con " + ticketWithUserDTO.getUserIdentifier())));

        List<Ticket> tickets = this.ticketService.convertToEntitiesNoSecure(ticketWithUserDTO);

        for(Ticket t: tickets){
            System.out.println(t);
        }

        Booking booking = new Booking();
        System.out.println("nuevo booking "+ booking);
        booking.setId(IdGenerator.randomString(9));
        booking.setUser(user);
        booking = bookingService.save(booking);

        System.out.println("booking con identificador generado"+ booking);

        List<FullTicketWithDetailsDTO> fullTickets = new ArrayList<>();

        System.out.println("vamos a recorrer los tickets del dto y crearlos");

        for (Ticket t: tickets){
            t.setBooking(booking);

            ScreeningDayAndHourDTO sdahDTO = this.screeningService.toScreeningDayAndHourDTO(t.getScreening().getStart_time());
            double ticketPrice = this.ticketService.calculateTickePrice(t);

            FullTicketWithDetailsDTO fullTicket =
                    FullTicketWithDetailsDTO.builder()
                            .identifier(t.getBooking().getId())
                            .screeningDTO(ScreeningDTO.builder()
                                    .Id(t.getScreening().getId())
                                    .screen(Math.toIntExact(t.getScreening().getScreen().getId()))
                                    .movieTitle(t.getScreening().getMovie().getTitle())
                                    .screeningDayAndHourDTO(sdahDTO)
                                    .cinemaName(t.getScreening().getScreen().getCinema().getName())
                                    .screenPrice(t.getScreening().getPrice())
                                    .audio(t.getScreening().getAudio())
                                    .build())
                            .rowAndSeat("F "+t.getSeat().getScreenRows().getRowNumber() +" B "+ t.getSeat().getSeatNumber())
                            .totalPrice(ticketPrice)
                            .build();

            System.out.println("fullticket DTO"+ sdahDTO);

            fullTickets.add(fullTicket);

            Ticket savedSTicket = this.ticketService.save(t);
        }

        if (fullTickets.isEmpty()){
            throw new RuntimeException("No hay asientos disponibles");
        }else{
            try {
                byte[] pdfBytes = pdfService.generatePdfOfFullTicket(fullTickets);
                emailService.sendEmailWithPdf("brymonga@gmail.com",
                        "Entrada de Cine - FilMMes",
                        "Muchas gracias por efectuar su compra.",
                        booking.getId(), pdfBytes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok(fullTickets);
    }

    @PostMapping("/screenings/{screeningId}/seat/{seatId}/buy")
    public ResponseEntity<Ticket> buyTicketFromScreeningById(@PathVariable Long screeningId,
                                                             @PathVariable Long seatId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByNickname(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));



        Ticket ticket = this.ticketService.convertToEntity(TicketDTO.builder()
                                                            .screening_id(screeningId)
                                                            .seat_id(seatId)
                                                            .build());

        if(user.getId()!=null){
            Booking booking = new Booking();
            System.out.println(booking);
            booking.setId(IdGenerator.randomString(9));
            booking.setUser(user);
            booking = bookingService.save(booking);

            ticket.setBooking(booking);
        }

        System.out.println(ticket);

        Ticket savedSTicket = this.ticketService.save(ticket);
        return ResponseEntity.ok(savedSTicket);
    }

    @PostMapping("/sendEmailTest")
    public ResponseEntity<String> sendEmailTest(@Valid @RequestBody String email){
        try {
            this.emailService.sendEmail("brymonga@gmail.com", "probando", email);
        }catch (MessagingException e){
            throw new RuntimeException("Algo fallo");
        }
        return ResponseEntity.ok("Correo envia satisfactoriamente");
    }

    @PostMapping("/sendEmailWithQR")
    public ResponseEntity<String> sendEmailWithQR(@Valid @RequestBody String embailBody) {

        try {
            String identifier = "ABCDEFGHI"; // Usa tu lógica para generar el identificador
            emailService.sendEmailWithQRCode("brymonga@gmail.com",
                                            "Mensaje enviado desde springboot",
                                                embailBody, identifier);
        } catch (MessagingException | IOException | WriterException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok("Todo va bien");
    }


    @PostMapping("/sendEmailWithPDF")
    public ResponseEntity<String> sendEmailWithPDF(@Valid @RequestBody String embailBody) {

        try {
            String identifier = "ABCDEFGHI"; // Usa tu lógica para generar el identificador

            byte[] pdfBytes = pdfService.generatePdfWithQrCodeAndDesign(identifier,embailBody);

            emailService.sendEmailWithPdf("brymonga@gmail.com",
                    "Mensaje enviado desde spring",
                    "Estamos haciendo la petición desde postman",
                        identifier, pdfBytes);
        } catch (MessagingException | IOException | WriterException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Todo va bien");
    }
}
