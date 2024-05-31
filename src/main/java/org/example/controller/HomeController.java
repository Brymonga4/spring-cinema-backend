package org.example.controller;

import org.example.model.AdminLink;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/public")
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "HOME IS HOME!";
    }
    @PostMapping("/home")
    public ResponseEntity<String> postHome() {

        return ResponseEntity.ok("You entered home safely.");
    }

}