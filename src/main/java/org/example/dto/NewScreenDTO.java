package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NewScreenDTO {
    private Long cinema_id;
    private String supports;
    private int rows;
    private int numberOfSeats;
}
