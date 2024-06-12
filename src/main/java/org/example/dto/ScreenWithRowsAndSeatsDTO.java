package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ScreenWithRowsAndSeatsDTO {
    private Long screen_id;
    private List<RowsWithSeatsDTO> rowsWithSeatsDTOS;
}
