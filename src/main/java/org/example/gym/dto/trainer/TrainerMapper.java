package org.example.gym.dto.trainer;

import lombok.extern.slf4j.Slf4j;
import org.example.gym.dto.trainee.TraineeDTO;
import org.example.gym.dto.trainer.TrainerProfile;
import org.example.gym.entity.Trainee;
import org.example.gym.entity.Trainer;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TrainerMapper {
    public static TrainerProfile toProfile(Trainer trainer) {
        if (trainer == null) {
            log.warn("Provided Trainer object is null, returning null.");
            return null;
        }
        log.debug("Mapping Trainer [{}] to TrainerProfile.", trainer);
        TrainerProfile trainerProfile = new TrainerProfile();
        trainerProfile.setFirstName(trainer.getUser().getFirstName());
        trainerProfile.setLastName(trainer.getUser().getLastName());
        trainerProfile.setUsername(trainer.getUser().getUsername());
        trainerProfile.setActive(trainer.getUser().isActive());
        trainerProfile.setSpecialization(trainer.getSpecialization());
        log.debug("Mapped basic user info: firstName [{}], lastName [{}], username [{}], active [{}].",
                trainerProfile.getFirstName(), trainerProfile.getLastName(), trainerProfile.getUsername(),
                trainerProfile.isActive());
        Set<TraineeDTO> trainees=new HashSet<>();
        for (Trainee trainee:trainer.getTrainees()) {
            trainees.add(new TraineeDTO(trainee.getUser().getUsername(),trainee.getUser().getFirstName(),trainee.getUser().getLastName()));
        }
        trainerProfile.setTrainees(trainees);
        return trainerProfile;
    }

//    public static Trainer trainerProfileToTrainer(TrainerProfile trainerProfile) {
//        log.debug("Mapping TrainerProfile [{}] to Trainer.", trainerProfile);
//    }
}
