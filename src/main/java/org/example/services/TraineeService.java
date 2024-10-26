package org.example.services;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.TraineeRepository;
import org.example.model.Trainee;
import org.example.model.User;
import org.example.profiles.TraineeMapper;
import org.example.profiles.TraineeProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TraineeService {
    private TraineeRepository traineeRepository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    @Transactional
    public Optional<TraineeProfile> create(@NonNull String firstName, @NonNull String lastName, String address, LocalDate dateOfBirth) {
        validateNames(firstName, lastName);
        log.info("Creating a new Trainer: {} {}", firstName, lastName);
        Trainee trainee = new Trainee(new User(firstName, lastName, generateUserName(firstName, lastName), generatePassword(), false), address, dateOfBirth);
        traineeRepository.save(trainee);
        log.info("Trainer created with username: {}", trainee.getUser().getUsername());
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    public Optional<TraineeProfile> findByUsername(String username, String password) {
        authenticate(username, password);
        log.info("Searching Trainee by username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    private void authenticate(String username, String password) {
        if (traineeRepository.findByUsernameAndPassword(username, password).isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    @Transactional
    private Trainee findTraineeByUsername(String username) {
        return traineeRepository.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainee with username: " + username + " not found."));
    }

    @Transactional
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        authenticate(username, oldPassword);
        log.info("Changing password of Trainee with username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        trainee.getUser().setPassword(newPassword);
        log.info("Password successfully changed");
        return true;
    }

    @Transactional
    public Optional<TraineeProfile> update(String firstName, String lastName, String username, String password, String address, LocalDate dateOfBirth, boolean isActive) {
        authenticate(username, password);
        log.info("Updating Trainee's data with username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        trainee.getUser().setFirstName(firstName);
        trainee.getUser().setLastName(lastName);
        trainee.getUser().setActive(isActive);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        log.info("Trainee's data with username: {} has been updated", username);
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    public void delete(String username, String password) {
        authenticate(username, password);
        log.info("Deleting Trainee with username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        traineeRepository.delete(trainee);
        log.info("Trainee with username: {} successfully deleted", username);
    }

    public boolean activate(String username, String password) {
        authenticate(username, password);
        log.info("Activating Trainee with username: {}", username);
        boolean activated=updateActiveStatus(username, true);
        log.info("Trainee with username: {} activated", username);
        return activated;
    }

    public boolean deactivate(String username, String password) {
        authenticate(username, password);
        log.info("Deactivating Trainee with username: {}", username);
        boolean deactivated=updateActiveStatus(username, false);
        log.info("Trainee with username: {} deactivated", username);
        return deactivated;
    }
    @Transactional
    private boolean updateActiveStatus(String username, boolean isActive) {
        Trainee trainee = findTraineeByUsername(username);
        trainee.getUser().setActive(isActive);
        return true;
    }

    private void validateNames(String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            log.error("Trainee creation failed: blank firstname or lastname");
            throw new IllegalArgumentException("Trainee without firstname and lastname cannot be created!");
        }
    }

    private String generateUserName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        long count = traineeRepository.findAll().stream()
                .filter(tr -> tr.getUser().getFirstName().equals(firstName) && tr.getUser().getLastName().equals(lastName))
                .count();
        return count > 0 ? baseUserName + count : baseUserName;
    }

    private String generatePassword() {
        Random random = new Random();
        return random.ints(33, 122)
                .filter(Character::isLetter)
                .limit(10)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
