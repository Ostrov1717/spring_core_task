package org.example.gym.dto.trainer;

import org.example.gym.entity.TrainingType;

public record TrainerDTO(String username, String firstName, String lastName, TrainingType specialization) {
}
