package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketReceiptDTO {
    private String identifier;
    private String screeningDate;
    private String cinemaName;
    private String movieTitle;
    private double finalPrice;
}
