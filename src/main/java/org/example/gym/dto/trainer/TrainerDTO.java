package org.example.gym.dto.trainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gym.dto.trainee.TraineeDTO;
import org.example.gym.entity.TrainingType;

import java.util.Set;

public enum TrainerDTO {;
    private interface Username {String getUsername();}
    private interface Password {String getPassword();}
    private interface FirstName {String getFirstName();}
    private interface LastName {String getLastName();}
    private interface Specialization {TrainingType getSpecialization();}
    private interface Active {boolean isActive();}
    private interface Trainees {Set<TraineeDTO.Response.TraineeSummury> getTrainees();}

    public enum Request {;
        @Data
        public static class Create implements FirstName, LastName,Specialization {
            @NotBlank(message = "First name is required")
            String firstName;
            @NotBlank(message = "Last name is required")
            String lastName;
            @NotNull (message = "Trainer specialization is required")
            TrainingType specialization;
        }
        @Data
        public static class Update implements Username, Password, FirstName, LastName, Specialization, Active {
            @NotBlank(message = "Username is required") String username;
            @NotBlank(message = "Password is required") String password;
            @NotBlank(message = "First name is required") String firstName;
            @NotBlank(message = "Last name is required") String lastName;
            @NotNull(message = "Trainer specialization is required") TrainingType specialization;
            @NotNull(message = "Trainer's status is required") boolean active;
        }
    }
    public enum Response{;
        @Data
        @AllArgsConstructor
        public static class TrainerProfile implements FirstName, LastName, Specialization,Trainees {
            String firstName;
            String lastName;
            TrainingType specialization;
            boolean active;
            Set<TraineeDTO.Response.TraineeSummury> trainees;
        }
        @Data
        @AllArgsConstructor
        public static class TrainerSummury implements Username,FirstName, LastName, Specialization {
            String username;
            String firstName;
            String lastName;
            TrainingType specialization;
        }
        @Data
        @AllArgsConstructor
        public static class TrainerUsername implements Username {
            String username;
        }
    }
}
