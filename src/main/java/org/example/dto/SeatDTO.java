package org.example.dto;

import lombok.*;
import org.example.model.Seat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SeatDTO {
    private Long id;
    private int row_number;
    private int seat_number;
    private char seat_type;
    private boolean available;

}
