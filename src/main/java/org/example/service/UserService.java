package org.example.service;


import org.example.model.Ticket;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //FIND
    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    Optional<User> findByNickname(String username);

    Optional<User> findByEmail(String email);

    // CREATE
    User save(User user);

    List<User> saveAll(List<User> users);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    User update(User user);


    boolean comparePassword(String rawPass, String encodedPass);


    List<Ticket> findAllTicketsBoughtByUserId(Long userId);

    List<Ticket> findTickesOfAUser(Long userId);

    Optional<User> findByNicknameAndRecoverCode(String nickname, String recoverCode);
    Optional<User> findByEmailAndRecoverCode(String email, String recoverCode);

}
