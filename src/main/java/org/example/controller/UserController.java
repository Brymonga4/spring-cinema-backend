package org.example.controller;

import jakarta.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.*;
import org.example.exception.Exceptions;
import org.example.model.*;
import org.example.service.TicketService;
import org.example.service.UserService;
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
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    public UserController(UserService userService, TicketService ticketService){
        this.userService = userService;
        this.ticketService = ticketService;
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

        List <Ticket> tickets = this.userService.findTickesOfAUser(id);

        List <TicketReceiptDTO> ticketsReceiptDTO = new ArrayList<>();

        for(Ticket t: tickets){
           ticketsReceiptDTO.add(t.ticketReceiptDTO());
        }

        if (ticketsReceiptDTO.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ticketsReceiptDTO);

    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){

        User user = this.userService.findById(id).orElseThrow(()-> new RuntimeException("No existe un usario con el id "+id));

        if (user==null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user.toUserResponseDTO());

    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody User user){
        User savedUser;
        if(!this.userService.existsByEmail(user.getEmail())) {
            if (!this.userService.existsByNickname(user.getNickname())) {
                 savedUser = this.userService.save(user);
            }else {
                throw new Exceptions.NicknameAlreadyUsedException("El nickname ya está siendo usado por otro usuario");
            }
        }else {
            throw new Exceptions.EmailAlreadyUsedException("El email ya está siendo usado por otro usuario");
        }

        return ResponseEntity.ok(savedUser.toUserResponseDTO());
    }


    @PostMapping(value = "users/{i}/uploadAvatar", consumes = "multipart/form-data")
    public ResponseEntity<UserResponseDTO> uploadAvatar(@PathVariable Long id,
                                           @RequestPart("file") MultipartFile file){
        User user = this.userService.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontró el usuario"));

        handleFileUploadUser(user.getNickname(),file);

        return ResponseEntity.ok(user.toUserResponseDTO());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody User user){

        if(this.userService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        user.setId(id);
        //Hacer DTO sin contraseña
        User updatedUser = this.userService.update(user);

        return ResponseEntity.ok(updatedUser.toUserResponseDTO());
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginNoSecurity(@Valid @RequestBody UserDTO userDTO){


        User user = this.userService.findByNickname(userDTO.getIdentifier())
                .orElseGet(() -> this.userService.findByEmail(userDTO.getIdentifier())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con " + userDTO.getIdentifier())));


        if(!this.userService.comparePassword(userDTO.getPassword(), user.getPassword()))
            return ResponseEntity.badRequest().build();


        return ResponseEntity.ok(user.toUserResponseDTO());
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

    private void handleFileUploadUser(String nickname, MultipartFile file) {

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Fallo en el directorio");
                }
            }

            if (Objects.equals(file.getOriginalFilename(), "")) {
                throw new RuntimeException("Título de archivo vacío");
            }

            String sanitizedTitle = sanitizeTitle(nickname);
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = "avatar_"+sanitizedTitle + fileExtension;

            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Thumbnails.of(file.getInputStream())
                    .size(120, 120)
                    .toFile(filePath.toFile());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al subir el archivo");
        }
    }

}
