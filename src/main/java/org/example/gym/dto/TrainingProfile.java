package org.example.gym.dto;

import org.example.gym.entity.TrainingType;

import java.time.Duration;
import java.time.LocalDateTime;

public class TrainingProfile {
    private String trainingName;

    private TrainingType trainingType;

    private LocalDateTime trainingDate;

    private Duration trainingDuration;
}
