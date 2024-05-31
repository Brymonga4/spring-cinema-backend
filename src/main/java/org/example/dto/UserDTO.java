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
    private String nickname;
    private String email;
    private String password;

    public User toEntity() {

        User user = new User();

        user.setNickname(this.nickname);
        user.setEmail(this.email);
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
