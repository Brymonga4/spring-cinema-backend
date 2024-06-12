package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FullTicketWithDetailsDTO {
    private String identifier;

    private ScreeningDTO screeningDTO;

    private String rowAndSeat;
    private double totalPrice;

}
