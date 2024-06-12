package org.example.dto;

import lombok.*;
import org.example.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    private String identifier;
    private String password;

    public User toEntity() {

        User user = new User();

        user.setNickname(this.identifier);
        user.setEmail(this.identifier);
        user.setPassword(this.password);

        user.setId(0L);
        user.setName("Juan");
        user.setSurname("Delacruz");
        user.setPhone("666666666");
        user.setPoints(0L);
        user.setPremium(false);
        user.setAdmin(false);
        user.setToken(null);
        user.setRecover_code(null);

        return user;
    }
}
