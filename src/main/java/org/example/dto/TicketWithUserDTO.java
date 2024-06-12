package org.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketWithUserDTO {
    private List<Long> seats;
    private Long screening_id;
    private String userIdentifier;
}
