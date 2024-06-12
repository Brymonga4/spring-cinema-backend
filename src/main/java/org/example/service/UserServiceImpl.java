package org.example.service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import net.coobird.thumbnailator.Thumbnails;
import org.example.dto.TicketReceiptDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserRecoverCodeDTO;
import org.example.dto.UserResponseDTO;
import org.example.exception.Exceptions;
import org.example.mapper.TicketMapper;
import org.example.mapper.UserMapper;
import org.example.model.Booking;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.repository.*;
import org.example.service.email.EmailService;
import org.example.service.email.PdfService;
import org.example.util.IdGenerator;
import org.example.util.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.example.util.MovieTitleUtil.getFileExtension;
import static org.example.util.MovieTitleUtil.sanitizeTitle;

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

    private final EmailService emailService;
    private final UploadConfig uploadConfig;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           BookingRepository bookingRepository, SeatRepository seatRepository,
                           ScreeningRepository screeningRepository, MovieRepository movieRepository,
                           CinemaRepository cinemaRepository, ScreenRepository screenRepository,
                           EmailService emailService, UploadConfig uploadConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.screenRepository = screenRepository;
        this.emailService = emailService;
        this.uploadConfig = uploadConfig;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new Exceptions.UserNotFoundException(id.toString()));
        return user;

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
    public List<TicketReceiptDTO> findTickesOfAUser(Long id) {


        List <Ticket> tickets =  this.userRepository.findTickesOfAUser(id);
        List <TicketReceiptDTO> ticketsReceiptDTO = new ArrayList<>();

        for(Ticket t: tickets){

            System.out.println(t);
            t.setBooking(this.bookingRepository.findByStrId(t.getBooking().getId())
                    .orElseThrow(()->new Exceptions.BookingNotFoundException(t.getBooking().getId())));

            t.setSeat(this.seatRepository.findById(t.getSeat().getIdSeat())
                    .orElseThrow(()->new Exceptions.SeatNotFoundException(t.getSeat().getIdSeat().toString())));

            t.setScreening(this.screeningRepository.findById(t.getScreening().getId())
                    .orElseThrow(()->new Exceptions.ScreeningNotFoundException(t.getScreening().getId().toString())));

            t.getScreening().setScreen(this.screenRepository.findById(t.getScreening().getScreen().getId())
                    .orElseThrow(()->new Exceptions.ScreenNotFoundException(t.getScreening().getScreen().getId().toString())));

            t.getScreening().getScreen().setCinema(this.cinemaRepository.findById(t.getScreening().getScreen().getCinema().getId())
                    .orElseThrow(()->new Exceptions.CinemaNotFoundException(t.getScreening().getScreen().getCinema().getId().toString())));

            t.getScreening().setMovie(this.movieRepository.findById(t.getScreening().getMovie().getId())
                    .orElseThrow(()->new Exceptions.MovieNotFoundException(t.getScreening().getMovie().getId().toString())));

            ticketsReceiptDTO.add(TicketMapper.toTicketReceiptDTO(t));
            System.out.println(t);
        }


        return  ticketsReceiptDTO;
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
    public UserResponseDTO uploadAvatarOfUser(Long id, MultipartFile file) {

        User user = this.userRepository.findById(id)
                .orElseThrow(()->new Exceptions.UserNotFoundException(id.toString()));
        fileUpload(user.getNickname(),file);


        return UserMapper.toUserResponseDTO(user);
    }

    public void fileUpload(String nickname, MultipartFile file){

        Path uploadPath = Paths.get(uploadConfig.getUploadDir());

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                throw new Exceptions.EmptyFileNameTitleException("");
            }

            String fileName = sanitizeTitle(nickname) + getFileExtension(file.getOriginalFilename());

            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Thumbnails.of(file.getInputStream())
                    .size(120, 120)
                    .toFile(filePath.toFile());

        } catch (IOException e) {
            throw new Exceptions.FileErrorException(e.getMessage());
        }
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

        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new Exceptions.EmailAlreadyUsedException(user.getEmail());
        }

        if (this.userRepository.existsByNickname(user.getNickname())) {
            throw new Exceptions.NicknameAlreadyUsedException(user.getNickname());
        }

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
    public UserResponseDTO recoverPassword(UserRecoverCodeDTO userRecoverCodeDTO){
        System.out.println(userRecoverCodeDTO.getNewPassword());

        User userToRecover = this.userRepository.findByNicknameAndRecoverCode(userRecoverCodeDTO.getIdentifier(), userRecoverCodeDTO.getRecover_code())
                .or(() -> this.userRepository.findByEmailAndRecoverCode(userRecoverCodeDTO.getIdentifier(), userRecoverCodeDTO.getRecover_code()))
                .orElseThrow(() -> new Exceptions.RecoverErrorException(userRecoverCodeDTO.getIdentifier()));

        userToRecover.setPassword(userRecoverCodeDTO.getNewPassword());

        User updatedUser = this.update(userToRecover);

        System.out.println(updatedUser.getPassword());

        return UserMapper.toUserResponseDTO(updatedUser);
    }

    @Override
    public void generateRecoverCode(String identifier){

        User userToRecover = this.userRepository.findByNickname(identifier)
                .or(() -> this.userRepository.findByEmail(identifier))
                .orElseThrow(() -> new Exceptions.UserNotFoundException(identifier));

        String recoverCode = IdGenerator.randomNumericString(6);

        userToRecover.setRecover_code(recoverCode);
        this.userRepository.save(userToRecover);

        this.sendEmailTo(userToRecover.getEmail(), userToRecover.getRecover_code());

    }

    @Override
    public void sendEmailTo(String emailTo, String recoverCode){
        try {
            this.emailService.sendEmail(emailTo,
                    "Código de recuperación de su cuenta",
                    "Este es su código de recuperación : "
                            + recoverCode);

        } catch (MessagingException e) {
            throw new Exceptions.EmailErrorException(e.getMessage());
        }
    }
    @Override
    public UserResponseDTO loginNoSecurity(UserDTO userDTO){

        User user = this.userRepository.findByNickname(userDTO.getIdentifier())
                .orElseGet(() -> this.userRepository.findByEmail(userDTO.getIdentifier())
                        .orElseThrow(() -> new Exceptions.UserNotFoundException(userDTO.getIdentifier())));

        if(!this.comparePassword(userDTO.getPassword(), user.getPassword())){
            return null;
        }

        return UserMapper.toUserResponseDTO(user);
    }




    @Override
    @Transactional
    public User update(User user) {
        this.userRepository.findAndLockById(user.getId());
        return this.userRepository.save(user);
    }

}
