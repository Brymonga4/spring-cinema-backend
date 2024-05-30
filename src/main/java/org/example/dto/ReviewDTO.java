package org.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Long user_id;
    private String title;
    private String opinion;
    private Short rating;
    private LocalDateTime review_date;
}
