package org.example.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewsAndAverageDTO {
    private List<ReviewDTO> reviews;
    double averageRating;
}
