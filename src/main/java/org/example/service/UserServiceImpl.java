package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.User;
import org.example.repository.UserRepository;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        return this.userRepository.existsByPassword(password);
    }

    @Override
    public Optional<User> findByNickname(String username) {
        System.out.println(username);

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
        return this.userRepository.save(user);
    }

}
