package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScreenWithSeatsDTO {

    private long screen_id;
    private List<SeatDTO> seats;

}
