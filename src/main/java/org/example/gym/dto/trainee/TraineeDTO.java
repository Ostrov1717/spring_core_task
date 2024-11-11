package org.example.gym.dto.trainee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gym.dto.trainer.TrainerDTO;

import java.time.LocalDate;
import java.util.Set;

public enum TraineeDTO {;
    private interface Username { String getUsername(); }
    private interface Password { String getPassword(); }
    private interface FirstName { String getFirstName(); }
    private interface LastName { String getLastName(); }
    private interface DateOfBirth { LocalDate getDateOfBirth(); }
    private interface Address { String getAddress(); }
    private interface Active { boolean isActive(); }
    private interface Trainers{ Set<TrainerDTO.Response.TrainerSummury> getTrainers();}
    private interface TrainersUsernames{ Set<TrainerDTO.Response.TrainerUsername> getTrainersUsernames();}

    public enum Request {;
        @Data public static class Create implements FirstName, LastName, DateOfBirth, Address {
            @NotBlank(message = "First name is required")
            String firstName;
            @NotBlank(message = "Last name is required")
            String lastName;
            LocalDate dateOfBirth;
            String address;
        }
        @Data public static class Update implements Username, Password, FirstName, LastName, DateOfBirth, Address, Active {
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
            @NotNull(message = "Trainee's status is required")
            boolean active;
        }
        @Data public static class UpdateTrainers implements Username, Password, TrainersUsernames {
            @NotBlank(message = "Username is required")
            String username;
            @NotBlank(message = "Password is required")
            String password;
            @NotNull(message = "Trainer's set is required")
            Set<TrainerDTO.Response.TrainerUsername> trainersUsernames;
        }
    }

    public enum Response {;
        @Data
        @AllArgsConstructor
        public static class TraineeProfile implements FirstName, LastName, DateOfBirth, Address, Active, Trainers {
            String firstName;
            String lastName;
            LocalDate dateOfBirth;
            String address;
            boolean active;
            Set<TrainerDTO.Response.TrainerSummury> trainers;
        }
        @Data
        @AllArgsConstructor
        public static class TraineeProfileFull implements Username, FirstName, LastName, DateOfBirth, Address, Active, Trainers {
            String username;
            String firstName;
            String lastName;
            LocalDate dateOfBirth;
            String address;
            boolean active;
            Set<TrainerDTO.Response.TrainerSummury> trainers;
        }
        @Data
        @AllArgsConstructor
        public static class TraineeSummury implements Username, FirstName, LastName {
            String username;
            String firstName;
            String lastName;
        }
    }
}
