package com.taingkea.fitness.service;

import com.taingkea.fitness.model.User;
import com.taingkea.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        if (!encoder.matches(password, user.getPassword())) return null;
        return user;
    }
}