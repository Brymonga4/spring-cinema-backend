package org.example.controller;

import org.example.model.AdminLink;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome to the admin dashboard!";
    }
    @GetMapping("/links")
    public List<AdminLink> getAdminLinks() {
        List<AdminLink> links = new ArrayList<>();
        links.add(new AdminLink("/api/movies", "Get all movies (GET)"));
        links.add(new AdminLink("/api/movies", "Create a new movie (POST)"));
        links.add(new AdminLink("/api/movies/{id}", "Update a movie (PUT)"));
        links.add(new AdminLink("/api/movies/{id}", "Delete a movie (DELETE)"));
        return links;
    }

}