package org.example.gym.dto;

import lombok.Data;
import org.example.gym.entity.Trainer;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TraineeProfile {

    private String firstName;

    private String lastName;

    private String username;

    private boolean active;

    private String address;

    private LocalDate dateOfBirth;

    private Set<TrainerDTO> trainers;

}
