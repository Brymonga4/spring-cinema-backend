package org.example.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.*;
import org.example.exception.Exceptions;
import org.example.mapper.UserMapper;
import org.example.model.*;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.example.util.IdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.util.MovieTitleUtil.getFileExtension;
import static org.example.util.MovieTitleUtil.sanitizeTitle;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final TicketService ticketService;

    private final EmailService emailService;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    public UserController(UserService userService, TicketService ticketService, EmailService emailService){
        this.userService = userService;
        this.ticketService = ticketService;
        this.emailService = emailService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> findAll(){

        List <User> users = this.userService.findAll();
        List<UserResponseDTO> userResponseDTOS = users.stream()
                .map(User::toUserResponseDTO).toList();

        if (users.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userResponseDTOS);

    }
    @GetMapping("/users/{id}/tickets")
    public ResponseEntity<List<TicketReceiptDTO>> findAllTicketsByUserId(@PathVariable Long id){

        List <TicketReceiptDTO> ticketsReceiptDTO  = this.userService.findTickesOfAUser(id);

        if (ticketsReceiptDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ticketsReceiptDTO);

    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){

        User user = this.userService.findById(id);

        return ResponseEntity.ok(user.toUserResponseDTO());

    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody User user){

        User savedUser = this.userService.save(user);

        return ResponseEntity.ok(savedUser.toUserResponseDTO());
    }


    @PostMapping(value = "users/{id}/uploadAvatar", consumes = "multipart/form-data")
    public ResponseEntity<UserResponseDTO> uploadAvatar(@PathVariable Long id,
                                           @RequestPart("file") MultipartFile file){

        UserResponseDTO userResponseDTO = this.userService.uploadAvatarOfUser(id, file);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody User user){

        if(this.userService.findById(id)==null)
            return ResponseEntity.badRequest().build();

        user.setId(id);
        //Hacer DTO sin contraseña
        User updatedUser = this.userService.update(user);

        return ResponseEntity.ok(UserMapper.toUserResponseDTO(updatedUser));
    }

    @PutMapping("/recoverPassword")
    public ResponseEntity<UserResponseDTO> recoverPassword(@Valid @RequestBody UserRecoverCodeDTO userRecoverCodeDTO){

        UserResponseDTO userRecovered = this.userService.recoverPassword(userRecoverCodeDTO);

        return ResponseEntity.ok(userRecovered);
    }


    @PostMapping("/generateRecoverCode")
    public ResponseEntity<Void> recoverPassword(@Valid @RequestBody String userIdentifier){

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginNoSecurity(@Valid @RequestBody UserDTO userDTO){

        UserResponseDTO userResponseDTO = this.userService.loginNoSecurity(userDTO);

        if(userResponseDTO == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login/user")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody UserDTO userDTO){

        User user = new User();

        if(this.userService.existsByNickname(userDTO.getIdentifier())){
            user = this.userService.findByNickname(userDTO.getIdentifier())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con "+ userDTO.getIdentifier()));
        }else{
            if(this.userService.existsByEmail(userDTO.getIdentifier())){
                user = this.userService.findByEmail(userDTO.getIdentifier())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con "+ userDTO.getIdentifier()));
            }
        }

        if(!this.userService.existsByPassword(userDTO.getPassword()))
            return ResponseEntity.badRequest().build();


        return ResponseEntity.ok(user.toUserResponseDTO());
    }
    @DeleteMapping ("/users/{identifier}")
    public ResponseEntity<User> deleteById(@PathVariable("identifier") Long id){

        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
