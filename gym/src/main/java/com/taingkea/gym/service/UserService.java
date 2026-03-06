package com.taingkea.gym.service;

import com.taingkea.gym.model.User;
import com.taingkea.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email.toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    // Returns the User if credentials match, null otherwise
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email.toLowerCase().trim());
        if (user == null) return null;
        if (!passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }
}