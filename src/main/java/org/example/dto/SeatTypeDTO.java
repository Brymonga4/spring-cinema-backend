package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SeatTypeDTO {
    private Long id;
    private char seat_type;
}
