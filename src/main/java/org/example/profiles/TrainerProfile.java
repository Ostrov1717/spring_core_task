package org.example.profiles;

import lombok.Data;
import org.example.model.Trainee;
import org.example.model.Training;
import org.example.model.TrainingType;

import java.util.Set;
@Data
public class TrainerProfile {

    private String firstName;

    private String lastName;

    private String username;

    private boolean active;

    private TrainingType specialization;

//    private Set<Trainee> trainees;
//
//    private Set<Training> trainings;
}
