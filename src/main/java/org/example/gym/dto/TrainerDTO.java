package org.example.gym.dto;

import org.example.gym.entity.TrainingType;

public record TrainerDTO(String username, String firstName, String lastName, TrainingType specialization) {
}
