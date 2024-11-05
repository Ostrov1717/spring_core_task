package org.example.gym.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gym.dto.TrainerDTO;
import org.example.gym.dto.TrainerMapper;
import org.example.gym.dto.TrainerProfile;
import org.example.gym.entity.Trainer;
import org.example.gym.entity.TrainingType;
import org.example.gym.entity.User;
import org.example.gym.entity.TrainingTypeName;
import org.example.gym.exception.UserNotFoundException;
import org.example.gym.repository.TrainerRepository;
import org.example.gym.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserService userService;

    @Transactional
    public Trainer create(@NonNull String firstName, @NonNull String lastName, TrainingTypeName trainingTypeName) {
        validateNames(firstName, lastName);
        log.info("Creating a new Trainer: {} {}", firstName, lastName);
        TrainingType specialization = findTrainingType(trainingTypeName);
        String password = userService.generatePassword();
        String username = userService.generateUserName(firstName, lastName);
        Trainer trainer = new Trainer(specialization, new User(firstName, lastName, username, password, false));
        trainerRepository.save(trainer);
        log.info("Trainer created with username: {}", trainer.getUser().getUsername());
        return trainer;
    }

    @Transactional
    public TrainerProfile findByUsername(String username, String password) {
        userService.authenticate(username, password);
        log.info("Searching Trainer by username: {}", username);
        Trainer trainer = findTrainerByUsername(username);
        return TrainerMapper.toProfile(trainer);
    }

    @Transactional
    private Trainer findTrainerByUsername(String username) {
        return trainerRepository.findByUserUsername(username).orElseThrow(() -> new UserNotFoundException("Trainer with username: " + username + " not found."));
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        userService.authenticate(username, oldPassword);
        log.info("Changing password of Trainer with username: {}", username);
        Trainer trainer = findTrainerByUsername(username);
        trainer.getUser().setPassword(newPassword);
        log.info("Password successfully changed");
    }

    @Transactional
    public TrainerProfile update(String firstName, String lastName, String username, String password, TrainingTypeName trainingTypeName, boolean isActive) {
        userService.authenticate(username, password);
        log.info("Updating Trainer's data with username: {}", username);
        Trainer trainer = findTrainerByUsername(username);
        TrainingType specialization = findTrainingType(trainingTypeName);
        trainer.getUser().setFirstName(firstName);
        trainer.getUser().setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.getUser().setActive(isActive);
        log.info("Trainer's data with username: {} has been updated", username);
        return TrainerMapper.toProfile(trainer);
    }

    @Transactional
    public boolean activate(String username, String password) {
//        authenticate(username, password);
        log.info("Activating Trainer with username: {}", username);
        boolean activated = updateActiveStatus(username, true);
        log.info("Trainer with username: {} activated", username);
        return activated;
    }

    @Transactional
    private boolean updateActiveStatus(String username, boolean isActive) {
        Trainer trainer = findTrainerByUsername(username);
        trainer.getUser().setActive(isActive);
        return true;
    }

    @Transactional
    public boolean deactivate(String username, String password) {
//        authenticate(username, password);
        log.info("Deactivating Trainer with username: {}", username);
        boolean deactivated = updateActiveStatus(username, false);
        log.info("Trainer with username: {} deactivated", username);
        return true;
    }

    public Set<TrainerDTO> getAvailableTrainers(String traineeUsername) {
        log.info("Search trainers  that not assigned on trainee: {}", traineeUsername);
        Set<TrainerDTO> trainers = new HashSet<>();
        for (Trainer trainer : trainerRepository.findTrainersNotAssignedToTraineeByUsername(traineeUsername)) {
            trainers.add(new TrainerDTO(trainer.getUser().getUsername(), trainer.getUser().getFirstName(),trainer.getUser().getLastName(), trainer.getSpecialization());
        }
        log.info("Found {} trainers for trainee: {}", trainers.size(), traineeUsername);
        return trainers;
    }

    private void validateNames(String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            log.error("Trainer creation failed: blank firstname or lastname");
            throw new IllegalArgumentException("Trainer without firstname and lastname cannot be created!");
        }
    }

    private TrainingType findTrainingType(TrainingTypeName trainingTypeName) {
        return trainingTypeRepository.findByTrainingType(trainingTypeName.name())
                .orElseThrow(() -> new IllegalArgumentException("Specialization not found"));
    }

    @Transactional
    public Optional<TrainerProfile> findById(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trainee not found"));
        return Optional.of(TrainerMapper.toProfile(trainer));
    }

}
