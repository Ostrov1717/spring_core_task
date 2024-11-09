package org.example.gym.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gym.entity.Trainer;
import org.example.gym.entity.TrainingType;
import org.example.gym.entity.TrainingTypeName;
import org.example.gym.entity.User;
import org.example.gym.exception.UserNotFoundException;
import org.example.gym.repository.TrainerRepository;
import org.example.gym.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Trainer findByUsername(String username, String password) {
        userService.authenticate(username, password);
        log.info("Searching Trainer by username: {}", username);
        Trainer trainer = findTrainerByUsername(username);
        return trainer;
    }

    @Transactional
    public Trainer findTrainerByUsername(String username) {
        return trainerRepository.findByUserUsername(username).orElseThrow(() -> new UserNotFoundException("Trainer with username: " + username + " not found."));
    }

    @Transactional
    public Trainer update(String firstName, String lastName, String username, String password, TrainingTypeName trainingTypeName, boolean isActive) {
        userService.authenticate(username, password);
        log.info("Updating Trainer's data with username: {}", username);
        Trainer trainer = findTrainerByUsername(username);
        TrainingType specialization = findTrainingType(trainingTypeName);
        trainer.getUser().setFirstName(firstName);
        trainer.getUser().setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.getUser().setActive(isActive);
        log.info("Trainer's data with username: {} has been updated", username);
        return trainer;
    }
    public Set<Trainer> getAvailableTrainers(String traineeUsername, String password) {
        userService.authenticate(traineeUsername,password);
        log.info("Search trainers  that not assigned on trainee: {}", traineeUsername);
        Set<Trainer> trainers = trainerRepository.findTrainersNotAssignedToTraineeByUsername(traineeUsername);
        log.info("Found {} active trainers for trainee: {}", trainers.size(), traineeUsername);
        return trainers;
    }

    private TrainingType findTrainingType(TrainingTypeName trainingTypeName) {
        return trainingTypeRepository.findByTrainingType(trainingTypeName.name())
                .orElseThrow(() -> new IllegalArgumentException("Specialization not found"));
    }
    @Transactional
    public List<TrainingType> trainingTypes(){
        return trainingTypeRepository.findAll();
    }

}
