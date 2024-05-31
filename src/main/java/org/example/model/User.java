package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.UserDTO;
import org.example.dto.UserResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O GenerationType.AUTO
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Long points = 0L;

    @Column(nullable = false)
    private boolean premium = false;

    @Column(nullable = false)
    private boolean admin = false;

    @Column
    private String token;
    @Column
    private String recover_code;


    public UserDTO toDTO() {
        return UserDTO.builder()
                .nickname(this.nickname)
                .email(this.email)
                .build();
    }

    public UserResponseDTO toUserResponseDTO() {
        return UserResponseDTO.builder()
                .id(this.id)
                .nickname(this.nickname)
                .name(this.name)
                .surname(this.surname)
                .email(this.email)
                .phone(this.phone)
                .points(this.points)
                .premium(this.premium)
                .admin(this.admin)
                .recover_code(this.recover_code)
                .build();
    }
}
