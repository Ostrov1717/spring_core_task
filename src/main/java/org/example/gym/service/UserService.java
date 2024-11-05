package org.example.gym.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.gym.entity.Trainee;
import org.example.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void authenticate(String username, String password) {
        log.info("Attempting to authenticate Trainee with username: {}", username);
        boolean authenticated = userRepository.findByUsernameAndPassword(username, password).isPresent();
        if (!authenticated) {
            log.warn("Authentication failed for username: {}", username);
            throw new IllegalArgumentException("Invalid username or password");
        }
        log.info("Authentication successful for username: {}", username);
    }
    public String generatePassword() {
        Random random = new Random();
        return random.ints(33, 122)
                .filter(Character::isLetter)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
    @Transactional
    public String generateUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        long count = userRepository.findAll().stream()
                .filter(tr -> tr.getFirstName().equals(firstName) && tr.getLastName().equals(lastName))
                .count();
        return count > 0 ? baseUserName + count : baseUserName;
    }

}
