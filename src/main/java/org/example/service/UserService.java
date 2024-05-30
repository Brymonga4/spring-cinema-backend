package org.example.service;


import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //FIND
    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByNickname(String username);

    // CREATE
    User save(User user);

    List<User> saveAll(List<User> users);

    // DELETE
    void deleteById(Long id);
    void deleteAll();

    // UPDATE
    User update(User user);

}
