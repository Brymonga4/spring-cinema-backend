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

    public Seat() {
    }

    public Long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Long idSeat) {
        this.idSeat = idSeat;
    }

    public Long getScreen() {
        return screen;
    }

    public void setScreen(Long screen) {
        this.screen = screen;
    }

    public Long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Long seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Double getType() {
        return type;
    }

    public void setType(Double type) {
        this.type = type;
    }
}
