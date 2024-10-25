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
    public Optional<TraineeProfile> findById(Long id) {
        log.info("Searching Trainer by Id: {}", id);
        Trainee trainee = traineeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trainee not found"));
        initializeLazyCollections(trainee);
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    public Optional<TraineeProfile> findByUsername(String username) {
        log.info("Searching Trainee by username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        initializeLazyCollections(trainee);
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (login(username, oldPassword)) {
            Trainee trainee = findTraineeByUsername(username);
            trainee.getUser().setPassword(newPassword);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<TraineeProfile> update(String firstName, String lastName, String username, String password, String address, LocalDate dateOfBirth, boolean isActive) {
        if (!login(username, password)) {
            return Optional.empty();
        }
        log.info("Updating Trainee's data with username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        trainee.getUser().setFirstName(firstName);
        trainee.getUser().setLastName(lastName);
        trainee.getUser().setActive(isActive);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        log.info("Trainee's data with username: {} has been updated", username);
        initializeLazyCollections(trainee);
        return Optional.of(TraineeMapper.toProfile(trainee));
    }

    @Transactional
    public void delete(String username, String password) {
        log.info("Deleting Trainee with username: {}", username);
        Trainee trainee = findTraineeByUsername(username);
        traineeRepository.delete(trainee);
        log.info("Trainee with username: {} successfully deleted", username);
    }

    @Transactional
    public boolean activate(String username, String password) {
        return updateActiveStatus(username, password, true);
    }

    @Transactional
    public boolean deactivate(String username, String password) {
        return updateActiveStatus(username, password, false);
    }

    @Transactional
    public boolean login(String username, String password) {
        Trainee trainee = findTraineeByUsername(username);
        return trainee.getUser().getPassword().equals(password);
    }

    private void validateNames(String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            log.error("Trainee creation failed: blank firstname or lastname");
            throw new IllegalArgumentException("Trainee without firstname and lastname cannot be created!");
        }
    }

    private Trainee findTraineeByUsername(String username) {
        return traineeRepository.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainee with username: " + username + " not found."));
    }

    private void initializeLazyCollections(Trainee trainee) {
        trainee.getTrainers().size();
        trainee.getTrainings().size();
    }

    private boolean updateActiveStatus(String username, String password, boolean isActive) {
        if (login(username, password)) {
            Trainee trainee = findTraineeByUsername(username);
            trainee.getUser().setActive(isActive);
            return true;
        }
        return false;
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
