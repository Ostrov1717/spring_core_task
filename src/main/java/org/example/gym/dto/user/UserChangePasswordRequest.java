package org.example.gym.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordRequest(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Old password is required")
        String oldPassword,

        @NotBlank(message = "New password is required")
        String newPassword
){
}
