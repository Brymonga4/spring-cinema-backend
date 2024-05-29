package org.example.service;

import org.example.model.ScreenRows;
import org.example.repository.Screen_rowsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Screen_rowsServiceImpl implements Screen_rowsService{
    private Screen_rowsRepository repository;

    public Screen_rowsServiceImpl (Screen_rowsRepository repository){
        this.repository = repository;
    }
    @Override
    public List<ScreenRows> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<ScreenRows> findById(Long id) {
        return this.repository.findById(id);
    }



    @Override
    public ScreenRows save(ScreenRows screenRows) {
        return this.repository.save(screenRows);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }
    @Override
    public List<ScreenRows> findAllByScreenId(Long id){
        return this.repository.findAllByScreenId(id);
    }


    @Override
    public ScreenRows update(ScreenRows screenRows) {
        this.repository.findAndLockById(screenRows.getId());
        return this.save(screenRows);
    }
}
