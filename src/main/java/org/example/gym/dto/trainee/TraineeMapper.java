package org.example.gym.dto.trainee;

import lombok.extern.slf4j.Slf4j;
import org.example.gym.dto.trainer.TrainerDTO;
import org.example.gym.entity.Trainee;
import org.example.gym.entity.Trainer;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TraineeMapper {
    public static TraineeProfile toProfile(Trainee trainee) {
        if (trainee == null) {
            log.warn("Provided Trainee object is null, returning null.");
            return null;
        }
        log.debug("Mapping Trainee [{}] to TraineeProfile.", trainee);

        TraineeProfile traineeProfile = new TraineeProfile();
        traineeProfile.setFirstName(trainee.getUser().getFirstName());
        traineeProfile.setLastName(trainee.getUser().getLastName());
        traineeProfile.setUsername(trainee.getUser().getUsername());
        traineeProfile.setActive(trainee.getUser().isActive());
        traineeProfile.setAddress(trainee.getAddress());
        traineeProfile.setDateOfBirth(trainee.getDateOfBirth());

        log.debug("Mapped basic user info: firstName [{}], lastName [{}], username [{}], active [{}].",
                traineeProfile.getFirstName(), traineeProfile.getLastName(), traineeProfile.getUsername(),
                traineeProfile.isActive());
        Set<TrainerDTO> trainers=new HashSet<>();
        for (Trainer trainer:trainee.getTrainers()) {
            trainers.add(new TrainerDTO(trainer.getUser().getFirstName(),trainer.getUser().getLastName(),trainer.getUser().getUsername(),trainer.getSpecialization()));
        }
        traineeProfile.setTrainers(trainers);
        return traineeProfile;
    }
}
