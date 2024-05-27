package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Screening;
import org.example.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ScreeningServiceImpl implements ScreeningService {

    private ScreeningRepository repository;

    public ScreeningServiceImpl(ScreeningRepository repository){ this.repository = repository;}

    @Override
    public List<Screening> findAll() {

        return this.repository.findAll();

    }

    @Override
    public Optional<Screening> findById(Long id) {
        return this.repository.findById(id);
    }

    public List<Screening> findAllByMovie(Long id) {
        return this.repository.findAllByMovie_Id(id);
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
    public Screening update(Screening screening) {
        this.repository.findAndLockById(screening.getId());
        return this.save(screening);
    }

    public boolean isOverlapping(Screening newScreening){
        List<Screening> screenings = this.repository.findAllByScreen_Id(newScreening.getScreen().getId());

        for (Screening existing : screenings) {
            if (newScreening.getStart_time().isBefore(existing.getEndTime()) && newScreening.getEndTime().isAfter(existing.getStart_time())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Screening save(Screening screening){
        if (isOverlapping(screening)) {
            throw new IllegalStateException("Ya existe una función en ese espacio de tiempo.");
        }
        return repository.save(screening);
    }

    public List<Screening> findAllScreeningByScreenId(Long id){
        return repository.findAllByScreen_Id(id);
    }

}
