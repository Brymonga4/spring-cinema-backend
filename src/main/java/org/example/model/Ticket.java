package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.TicketReceiptDTO;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private Booking booking = null;

    @Column(nullable = false)
    private boolean available = true;


    public TicketReceiptDTO ticketReceiptDTO(){
        return TicketReceiptDTO.builder()
                .id(id)
                .screeningDate(screening.getDayFromStartTime() +", "+ screening.getStart_time())
                .cinemaName(screening.getScreen().getCinema().getName())
                .movieTitle(screening.getMovie().getTitle())
                .finalPrice(screening.getPrice() * seat.convert())
                .build();
    }

}
