package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RowsWithSeatsDTO {
    private Long row_id;
    private int row_num;
    private List<SeatDTO> seats;
}
