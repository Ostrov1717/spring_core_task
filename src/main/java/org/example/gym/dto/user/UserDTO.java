package org.example.gym.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password) {
}
