package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seat")
    private Long idSeat;

    @Column(name = "screen")
    private Long screen;

    @Column(name = "seat")
    private Long seatNumber;

    @Column(name = "row")
    private Long rowNumber;

    @Column(name = "type", nullable = false)
    private Double type = 1.0;


}
