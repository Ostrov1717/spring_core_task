package org.example.gym.dto;

import lombok.Data;
import org.example.gym.entity.TrainingType;

import java.util.Set;

@Data
public class TrainerProfile {

    private String firstName;

    private String lastName;

    private String username;

    private boolean active;

    private TrainingType specialization;

    private Set<TraineeDTO> trainees;

}
