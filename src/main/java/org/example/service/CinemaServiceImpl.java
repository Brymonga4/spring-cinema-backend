package org.example.service;

import org.example.model.Cinema;
import org.example.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CinemaServiceImpl implements  CinemaService{

    private final CinemaRepository repository;

    public CinemaServiceImpl(CinemaRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Cinema> findAll() {

        return this.repository.findAll();

    }

    @Override
    public Optional<Cinema> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Cinema save(Cinema cinema) {
        return this.repository.save(cinema);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public Cinema update(Cinema cinema) {
        return this.repository.save(cinema);
    }

    @Override
    public Optional<Cinema> findCinemaByName(String name) {
        return this.repository.findCinemaByName(name);
    }
}
