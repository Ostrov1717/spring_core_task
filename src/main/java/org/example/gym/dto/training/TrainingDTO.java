package org.example.gym.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.gym.entity.TrainingType;

import java.time.Duration;
import java.time.LocalDateTime;

public enum TrainingDTO {;
    private interface TraineeName {String getTraineeUsername();}
    private interface Password { String getPassword(); }
    private interface PeriodFrom{LocalDateTime getPeriodFrom();}
    private interface PeriodTo{LocalDateTime getPeriodTo();}
    private interface TrainerName {String getTrainerUsername();}
    private interface TrainingSpecial{TrainingType getTrainingType();}
    private interface TrainingName{String getTrainingName();}
    private interface TrainingDate{LocalDateTime getTrainingDate();}
    private interface TrainingDuration{Duration getTrainingDuration();}

    public enum Request{;
        @Value
        public static class Create implements TraineeName,TrainerName,TrainingName,TrainingDate,TrainingDuration{
            @NotBlank(message = "Trainee username is required")
            String traineeUsername;
            @NotBlank(message = "Trainer username is required")
            String trainerUsername;
            @NotBlank(message = "Training name is required")
            String trainingName;
            @NotNull(message = "Training date is required")
            LocalDateTime trainingDate;
            @NotNull(message = "Training duration is required")
            Duration trainingDuration;
        }
        @Value
        public static class TraineeTrainings implements TraineeName,Password,PeriodFrom,PeriodTo,TrainerName,TrainingSpecial{
        @NotBlank(message = "Trainee username is required")
        String traineeUsername;
        @NotBlank(message = "Password is required")
        String password;
        LocalDateTime periodFrom;
        LocalDateTime periodTo;
        String trainerUsername;
        TrainingType trainingType;
    }
        @Value
        public static class TrainerTrainings implements TrainerName,Password,PeriodFrom,PeriodTo,TraineeName,TrainingSpecial{
            @NotBlank(message = "Trainer username is required")
            String trainerUsername;
            @NotBlank(message = "Password is required")
            String password;
            LocalDateTime periodFrom;
            LocalDateTime periodTo;
            String traineeUsername;
            TrainingType trainingType;
        }
    }
    public enum Response{;
        @Value
        public static class TrainingProfileForTrainee implements TrainingName,TrainingDate,TrainingSpecial,TrainingDuration,TrainerName{
        String trainingName;
        LocalDateTime trainingDate;
        TrainingType trainingType;
        Duration trainingDuration;
        String trainerUsername;
    }
        @Value
        public static class TrainingProfileForTrainer implements TrainingName,TrainingDate,TrainingSpecial,TrainingDuration, TraineeName {
            String trainingName;
            LocalDateTime trainingDate;
            TrainingType trainingType;
            Duration trainingDuration;
            String traineeUsername;
        }
    }
}
