package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRecoverCodeDTO {
    private String identifier;
    private String recover_code;
    private String newPassword;
}
