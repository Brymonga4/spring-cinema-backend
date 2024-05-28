package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
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

}
