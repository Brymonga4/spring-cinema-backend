package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponseDTO {
    private Long id;
    private String nickname;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Long points;
    private boolean premium;
    private boolean admin;
    private String recover_code;
}
