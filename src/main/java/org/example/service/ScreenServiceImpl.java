package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Screen;
import org.example.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenServiceImpl implements ScreenService {

    private ScreenRepository repository;
    public ScreenServiceImpl (ScreenRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Screen> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Screen> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Screen save(Screen screen) {
        return this.repository.save(screen);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    @Transactional
    public Screen update(Screen screen) {
        this.repository.findAndLockById(screen.getId());
        return this.save(screen);
    }

}
