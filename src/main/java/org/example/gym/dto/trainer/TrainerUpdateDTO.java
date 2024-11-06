package org.example.gym.dto.trainer;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

public record TrainerUpdateDTO(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Trainer specialization is required")
        String specialization,

        @NotBlank(message = "Trainer's status is required")
        String active
) {
}
