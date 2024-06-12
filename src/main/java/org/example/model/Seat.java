package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.SeatDTO;
import org.example.dto.UserDTO;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long idSeat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "row_id", nullable = false)
    private ScreenRows screenRows;

    @Column(name = "seat_number")
    private Long seatNumber;

    @Column(name = "seat_type")
    private String seatType;

    @Column(name = "reserved")
    private Boolean reserved;


    public SeatDTO toDTOWithRowNumber(int rowNumber) {
        return SeatDTO.builder()
                .id(this.idSeat)
                .row_number(rowNumber)
                .seat_number(Math.toIntExact(this.seatNumber))
                .seat_type(this.seatType.charAt(0))
                .available(this.reserved)
                .build();
    }

    public double convert() {
        return switch (seatType.charAt(0)) {
            case 'N' -> 1.0;
            case 'P' -> 1.25;
            case 'W' -> 0.5;
            default -> throw new IllegalArgumentException("Carácter no válido ");
        };
    }

}
