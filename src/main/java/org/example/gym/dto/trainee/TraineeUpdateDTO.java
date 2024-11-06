package org.example.gym.dto.trainee;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

public record TraineeUpdateDTO(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        String dateOfBirth,

        String address,

        @NotBlank(message = "Trainer's status is required")
        String active
) {
}
