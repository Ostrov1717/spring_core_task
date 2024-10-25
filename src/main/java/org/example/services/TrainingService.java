package org.example.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.TrainingRepository;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainingService {

    private TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Optional<Training> create(@NonNull Trainee trainee, @NonNull Trainer trainer, @NonNull String trainingName, @NonNull TrainingType type, @NonNull LocalDateTime trainingDate, @NonNull Duration duration) {
        log.info("Creation of new training: traineeId={}, trainerId={}, name={}, type={}, date={}, duration={}",
                trainee.getUser().getId(), trainer.getUser().getId(), trainingName, type, trainingDate, duration);
        if (trainingName.isBlank()) {
            log.error("Error: trainingName cannot beblank");
            throw new IllegalArgumentException("Training name cannot be null/blank");
        }
        Training training = new Training(trainee, trainer, trainingName, type, trainingDate, duration);
        log.info("Training has been created: id={}, name={}", null, trainingName);
        return Optional.of(trainingRepository.save(training));
    }

    public Optional<Training> findByTrainingId(Long trainingId) {
        log.info("Search training by Id: {}", trainingId);
        return trainingRepository.findById(trainingId);
    }

    public List<Training> findByTrainer(String trainerUsername, LocalDateTime fromDate, LocalDateTime toDate, String traineeName) {
        log.info("Search trainings by trainer: {}",trainerUsername);
        return trainingRepository.findTrainingsByTrainerAndCriteria(trainerUsername,fromDate,toDate,traineeName);
    }
}
