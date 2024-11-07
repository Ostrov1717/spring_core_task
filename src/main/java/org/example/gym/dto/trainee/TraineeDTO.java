package org.example.gym.dto.trainee;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import org.example.gym.dto.trainer.TrainerDTO;

import java.time.LocalDate;
import java.util.Set;

public enum TraineeDTO {;
    // Интерфейсы для полей, включая те, которые получаются из User
    private interface Username { String getUsername(); }
    private interface Password { String getPassword(); }
    private interface FirstName { String getFirstName(); }
    private interface LastName { String getLastName(); }
    private interface DateOfBirth { LocalDate getDateOfBirth(); }
    private interface Address { String getAddress(); }
    private interface Active { boolean isActive(); }
    private interface Trainers{ Set<TrainerDTO.Response.TrainerSummury> getTrainers();}
    // Раздел для запросов (Request)
    public enum Request {;

        // DTO для создания нового Trainee, данные пользователя включены как составные
        @Value public static class Create implements FirstName, LastName, DateOfBirth, Address {
            @NotBlank(message = "First name is required")
            String firstName;
            @NotBlank(message = "Last name is required")
            String lastName;
            LocalDate dateOfBirth;
            String address;
        }

        // DTO для обновления профиля Trainee
        @Value public static class Update implements Username, Password, FirstName, LastName, DateOfBirth, Address, Active {
            @NotBlank(message = "Username is required")
            String username;
            @NotBlank(message = "Password is required")
            String password;
            @NotBlank(message = "First name is required")
            String firstName;
            @NotBlank(message = "Last name is required")
            String lastName;
            LocalDate dateOfBirth;
            String address;
            @NotBlank(message = "Trainee's status is required")
            boolean active;
        }
    }
    // Раздел для ответов (Response)
    public enum Response {;

        @Value public static class TraineeProfile implements FirstName, LastName, DateOfBirth, Address, Active, Trainers {
            String firstName;
            String lastName;
            LocalDate dateOfBirth;
            String address;
            boolean active;
            Set<TrainerDTO.Response.TrainerSummury> trainers;
        }
        @Value public static class TraineeProfileFull implements Username, FirstName, LastName, DateOfBirth, Address, Active, Trainers {
            String username;
            String firstName;
            String lastName;
            LocalDate dateOfBirth;
            String address;
            boolean active;
            Set<TrainerDTO.Response.TrainerSummury> trainers;
        }
        @Value public static class TraineeSummury implements Username, FirstName, LastName {
            String username;
            String firstName;
            String lastName;
        }
    }
}
