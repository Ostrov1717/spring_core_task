package org.example.services;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.TrainerRepository;
import org.example.dao.TrainingTypeRepository;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.User;
import org.example.model.enums.TrainingTypeName;
import org.example.profiles.TrainerMapper;
import org.example.profiles.TrainerProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class TrainerService {
    private final TrainerRepository dao;
    private final TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository) {
        this.dao = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Transactional
    public Optional<TrainerProfile> create(@NonNull String firstName, @NonNull String lastName, TrainingTypeName trainingTypeName) {
        log.info("Creation of new Trainer: {} {}", firstName, lastName);
        if (firstName.isBlank() || lastName.isBlank()) {
            log.error("Impossible to create new Trainer: blank firstname or lastname");
            throw new IllegalArgumentException("Trainer without firstname and lastname cannot be created !");
        }
        TrainingType specialization = trainingTypeRepository.findByTrainingType(trainingTypeName.name())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));
        String password = madePassword();
        String userName = madeUserName(firstName, lastName);
        log.info(String.valueOf(specialization));
        Trainer trainer = new Trainer(specialization, new User(firstName, lastName, userName, password, true));
        log.info("Trainer has been created with Id: {}, username: {}", null, userName);
        dao.save(trainer);
        return Optional.of(TrainerMapper.toProfile(trainer));
    }

    @Transactional
    public Optional<TrainerProfile> selectById(Long id) {
        log.info("Search Trainer by Id: {}", id);
        Trainer trainer = dao.findById(id).orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
        trainer.getTrainees().size();
        trainer.getTrainings().size();
        return Optional.ofNullable(TrainerMapper.toProfile(trainer));
    }

    @Transactional
    public Optional<TrainerProfile> selectByUsername(String username) {
        log.info("Search Trainer by username: {}", username);
        Trainer trainer = dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
        trainer.getTrainees().size();
        trainer.getTrainings().size();
        return Optional.ofNullable(TrainerMapper.toProfile(trainer));
    }

    public boolean login(String username, String password) {
        Trainer trainer = dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
        return trainer.getUser().getPassword().equals(password);
    }
    @Transactional
    public boolean changeLogin(String username, String oldPassword, String newPassword) {
        if (login(username, oldPassword)) {
            Trainer trainer=dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
            trainer.getUser().setPassword(newPassword);
            return true;
        }
        return false;
    }
    @Transactional
    public Optional<TrainerProfile> update(String firstName, String lastName, String username, String password,TrainingTypeName trainingTypeName, boolean isActive) {
        log.info("Updating Trainer's data with username: {}", username);
        if(login(username, password)){
            Trainer trainer = dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
            TrainingType specialization = trainingTypeRepository.findByTrainingType(trainingTypeName.name())
                    .orElseThrow(() -> new RuntimeException("Specialization not found"));
            trainer.getUser().setFirstName(firstName);
            trainer.getUser().setLastName(lastName);
            trainer.setSpecialization(specialization);
            trainer.getUser().setActive(isActive);
            log.info("Trainer's data with username: {} has been updated", username);
            trainer.getTrainees().size();
            trainer.getTrainings().size();
            return Optional.ofNullable(TrainerMapper.toProfile(trainer));
        }
        return Optional.empty();
    }
    @Transactional
    public boolean activate(String username,String password){
//        login(username, password)
        if(true){
            Trainer trainer = dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
            trainer.getUser().setActive(true);
            return true;
        }
        return false;
    }
    @Transactional
    public boolean deActivate(String username,String password){
        if(true){
            Trainer trainer = dao.findByUserUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer with username: " + username + " not found."));
            trainer.getUser().setActive(false);
            return true;
        }
        return false;
    }


    private String madeUserName(String firstName, String lastName) {
        String userName = firstName + "." + lastName;
        long alingments = dao.findAll().stream()
                .filter(un -> un.getUser().getFirstName().equals(firstName) && un.getUser().getLastName().equals(lastName))
                .count();
        if (alingments > 0) {
            userName += alingments;
        }
        return userName;
    }

    private String madePassword() {
        Random random = new Random();
        return Stream.generate(() -> (char) random.nextInt(33, 122))
                .filter(Character::isLetter)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
