package org.example.gym.dto.trainee;

import lombok.Data;
import org.example.gym.dto.trainer.TrainerDTO;

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
