package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Screening;
import org.example.repository.ScreeningRepository;
import org.example.util.DateComparison;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ScreeningServiceImpl implements ScreeningService {

    private ScreeningRepository repository;


    public ScreeningServiceImpl(ScreeningRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Screening> findAll() {

        return this.repository.findAll();

    }

    @Override
    public Optional<Screening> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Screening> findAllByScreenIdAndToday(Long screenId) {
        return this.repository.findAllByScreenIdAndToday(screenId);
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

    @Override
    public List<Screening> findScreeningsByToday() {
        return repository.findScreeningsByToday();
    }

    @Override
    public List<Screening> findAllByMovieIdAndToday(Long movieId) {
        return repository.findAllByMovieIdAndToday(movieId);
    }


    @Override
    public List<Screening> findScreeningsTodayAfterCurrentTimeStamp() {
        List<Screening> screenings = repository.findScreeningsTodayAfterCurrentTimeStamp();
        for (Screening s : screenings){
            System.out.println(s.getStart_time());
        }

        return repository.findScreeningsTodayAfterCurrentTimeStamp();
    }

    @Override
    public List<Screening> findAllByMovieIdAndNext7Days(Long movieId) {
        return repository.findAllByMovieIdAndNext7Days(movieId);
    }

    @Override
    public List<Screening> findFromNowToNext7Days() {
        return repository.findFromNowToNext7Days();
    }


}
