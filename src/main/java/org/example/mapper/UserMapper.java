package org.example.mapper;

import org.example.dto.TicketDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserRecoverCodeDTO;
import org.example.dto.UserResponseDTO;
import org.example.model.Screening;
import org.example.model.Seat;
import org.example.model.Ticket;
import org.example.model.User;

public class UserMapper {

    public static User toEntity(UserDTO userDTO){
        return User.builder()

                .nickname(userDTO.getIdentifier())
                .email(userDTO.getIdentifier())

                .password(userDTO.getPassword())

                .build();
    }

    public static UserRecoverCodeDTO toUserRecoverCodeDTO(User user, String identifier){

        return UserRecoverCodeDTO.builder()
                .identifier(identifier)
                .recover_code(user.getRecover_code())
                .newPassword(user.getPassword())

                .build();

    }
    public static UserResponseDTO toUserResponseDTO(User user){

        return UserResponseDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .points(user.getPoints())
                .premium(user.isPremium())
                .admin(user.isAdmin())
                .recover_code(user.getRecover_code())

                .build();

    }

}
