package org.example.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketDTO {
    private Long seat_id;
    private Long screening_id;
}