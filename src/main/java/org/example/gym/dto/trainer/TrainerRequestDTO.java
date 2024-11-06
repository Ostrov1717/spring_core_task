package org.example.gym.dto.trainer;

import jakarta.validation.constraints.NotBlank;
import org.example.gym.entity.TrainingType;

public record TrainerRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Trainer specialization is required")
        String specialization
) {
}
