package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Booking;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreenRepository screenRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BookingRepository bookingRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository, MovieRepository movieRepository, CinemaRepository cinemaRepository, ScreenRepository screenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return this.userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPassword(String password) {
        password = this.passwordEncoder.encode(password);

        System.out.println(password);
        return this.userRepository.existsByPassword(password);
    }
    @Override
    public boolean comparePassword(String rawPass, String encodedPass){
        return this.passwordEncoder.matches(rawPass,encodedPass );
    }


    @Override
    public List<Ticket> findAllTicketsBoughtByUserId(Long userId) {
        System.out.println(userId);

        List <Ticket> tickets =  this.userRepository.findAllTicketsBoughtByUserId(userId);

        for(Ticket t: tickets){
            t.setBooking(this.bookingRepository.findById(t.getId())
                    .orElseThrow(()->new RuntimeException("No existe esa reserva")));
            t.setSeat(this.seatRepository.findById(t.getSeat().getIdSeat())
                    .orElseThrow(()->new RuntimeException("No existe esa butaca")));

            t.setScreening(this.screeningRepository.findById(t.getScreening().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa función")));
            t.getScreening().setScreen(this.screenRepository.findById(t.getScreening().getScreen().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa sala")));
            t.getScreening().getScreen().setCinema(this.cinemaRepository.findById(t.getScreening().getScreen().getCinema().getId())
                    .orElseThrow(()->new RuntimeException("No existe ese cine")));
            t.getScreening().setMovie(this.movieRepository.findById(t.getScreening().getMovie().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa pelicula")));

            System.out.println(t);
        }
        return  tickets;

    }

    @Override
    public List<Ticket> findTickesOfAUser(Long id) {


        List <Ticket> tickets =  this.userRepository.findTickesOfAUser(id);

        for(Ticket t: tickets){

            System.out.println(t);
            t.setBooking(this.bookingRepository.findByStrId(t.getBooking().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa reserva")));
            t.setSeat(this.seatRepository.findById(t.getSeat().getIdSeat())
                    .orElseThrow(()->new RuntimeException("No existe esa butaca")));

            t.setScreening(this.screeningRepository.findById(t.getScreening().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa función")));
            t.getScreening().setScreen(this.screenRepository.findById(t.getScreening().getScreen().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa sala")));
            t.getScreening().getScreen().setCinema(this.cinemaRepository.findById(t.getScreening().getScreen().getCinema().getId())
                    .orElseThrow(()->new RuntimeException("No existe ese cine")));
            t.getScreening().setMovie(this.movieRepository.findById(t.getScreening().getMovie().getId())
                    .orElseThrow(()->new RuntimeException("No existe esa pelicula")));

            System.out.println(t);
        }
        return  tickets;
    }

    @Override
    public Optional<User> findByNicknameAndRecoverCode(String nickname, String recoverCode) {
        return this.userRepository.findByNicknameAndRecoverCode(nickname, recoverCode);
    }

    @Override
    public Optional<User> findByEmailAndRecoverCode(String email, String recoverCode) {
        return this.userRepository.findByEmailAndRecoverCode(email, recoverCode);
    }

    @Override
    public Optional<User> findByNickname(String username) {
        return this.userRepository.findByNickname(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return this.userRepository.saveAll(users);
    }

    @Override
    public void deleteById(Long id) {
         this.userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }




    @Override
    @Transactional
    public User update(User user) {
        this.userRepository.findAndLockById(user.getId());
        return this.save(user);
    }

}
