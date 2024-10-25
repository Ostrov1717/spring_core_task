package org.example.profiles;

import lombok.Data;
import org.example.model.Trainer;
import org.example.model.Training;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TraineeProfile {

    private String firstName;

    private String lastName;

    private boolean active;

    private String address;

    private LocalDate dateOfBirth;

    private Set<Trainer> trainers;

    private Set<Training> trainings;

}
