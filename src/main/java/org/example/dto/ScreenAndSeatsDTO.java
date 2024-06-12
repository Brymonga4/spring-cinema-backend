package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScreenAndSeatsDTO {
    private Long id;
    private String cinemaName;
    private String supports;
    private List<SeatDTO> seats;
}
