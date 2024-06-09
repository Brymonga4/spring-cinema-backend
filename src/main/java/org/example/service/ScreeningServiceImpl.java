package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.MovieDTO;
import org.example.dto.ScreenAndSeatsDTO;
import org.example.dto.ScreeningDayAndHourDTO;
import org.example.exception.Exceptions;
import org.example.model.Cinema;
import org.example.model.Movie;
import org.example.model.Screen;
import org.example.model.Screening;
import org.example.repository.CinemaRepository;
import org.example.repository.MovieRepository;
import org.example.repository.ScreenRepository;
import org.example.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository repository;
    private final ScreenRepository screenRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;


    public ScreeningServiceImpl(ScreeningRepository repository, ScreenRepository screenRepository, CinemaRepository cinemaRepository, MovieRepository movieRepository){
        this.repository = repository;
        this.screenRepository = screenRepository;
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
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
    public int countFromNowToNext7Days(Long id) {
        return this.repository.countFromNowToNext7Days(id);
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

    @Override
    public ScreeningDayAndHourDTO toScreeningDayAndHourDTO(LocalDateTime startTime) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = startTime.toLocalDate().format(dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = startTime.toLocalTime().format(timeFormatter);

        return ScreeningDayAndHourDTO.builder()
                                    .screeningDay(date)
                                    .screeningStartTime(time)
                                    .build();
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

        if(screening.getScreen().getId()!= null) {

            Screen screen = screenRepository.findById(screening.getScreen().getId())
                    .orElseThrow(() -> new Exceptions.ScreenNotFoundException(screening.getScreen().getId().toString()));
            screening.setScreen(screen);

            // Se puede cambiar
            Cinema cinema = this.cinemaRepository.findCinemaByName(screen.getCinema().getName())
                    .orElseThrow(()-> new Exceptions.CinemaNotFoundException(screen.getCinema().getId().toString()));
            screen.setCinema(cinema);
            screening.setScreen(screen);

            Movie movie = this.movieRepository.findMovieByTitle(screening.getMovie().getTitle())
                    .orElseThrow(() -> new Exceptions.MovieNotFoundException(screening.getMovie().getTitle()));
            screening.setMovie(movie);

            if (screening.getStart_time().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now()))) {
                throw new Exceptions.ScreeningTimeException(screening.getStart_time().toString());
            }

            if (isOverlapping(screening)) {
                throw new Exceptions.ScreeningTimeOverlapException(screening.getStart_time().toString());
            }

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
