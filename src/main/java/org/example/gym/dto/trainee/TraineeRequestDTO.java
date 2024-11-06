package org.example.gym.dto.trainee;

import jakarta.validation.constraints.NotBlank;

public record TraineeRequestDTO(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        String dateOfBirth,
        String address
) {
}
