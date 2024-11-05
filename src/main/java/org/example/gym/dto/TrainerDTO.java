package org.example.gym.dto;

import org.example.gym.entity.TrainingType;

public record TrainerDTO(String firstName, String lastName, String username, TrainingType specialization) {
}
