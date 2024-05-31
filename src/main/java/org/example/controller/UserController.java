package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserDTO;
import org.example.dto.UserResponseDTO;
import org.example.exception.Exceptions;
import org.example.model.ScreenRows;
import org.example.model.Seat;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){

        List <User> users = this.userService.findAll();

        if (users.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(users);

    }


    @PostMapping("/register")
    public ResponseEntity<User> create(@Valid @RequestBody User user){
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

        return ResponseEntity.ok(savedUser);
    }

    /*
    PUT http://localhost:8080/api/movies
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user){

        if(this.userService.findById(id).isEmpty())
            return ResponseEntity.badRequest().build();
        user.setId(id);
        //Hacer DTO sin contraseña
        User updatedUser = this.userService.update(user);

        return ResponseEntity.ok(updatedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginNoSecurity(@Valid @RequestBody UserDTO userDTO){

        if(!this.userService.existsByNickname(userDTO.getNickname()))
            if(!this.userService.existsByEmail(userDTO.getEmail()))
                if(!this.userService.existsByPassword(userDTO.getPassword()))
                     return ResponseEntity.badRequest().build();

        User user = this.userService.findByNickname(userDTO.getNickname())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(user.toUserResponseDTO());
    }
    @DeleteMapping ("/users/{identifier}")
    public ResponseEntity<User> deleteById(@PathVariable("identifier") Long id){

        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
