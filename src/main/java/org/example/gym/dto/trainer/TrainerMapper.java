package org.example.gym.dto.trainer;

import lombok.extern.slf4j.Slf4j;
import org.example.gym.dto.trainee.TraineeDTO;
import org.example.gym.entity.Trainee;
import org.example.gym.entity.Trainer;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TrainerMapper {
    public static TrainerDTO.Response.TrainerProfile toProfile(Trainer trainer) {
        if (trainer == null) {
            log.warn("Provided Trainer object is null, returning null.");
            return null;
        }
        log.debug("Mapping Trainer [{}] to TrainerProfile.", trainer);
        Set<TraineeDTO.Response.TraineeSummury> trainees=new HashSet<>();
        for (Trainee trainee:trainer.getTrainees()) {
            trainees.add(new TraineeDTO.Response.TraineeSummury(trainee.getUser().getUsername(),trainee.getUser().getFirstName(),trainee.getUser().getLastName()));
        }
        TrainerDTO.Response.TrainerProfile trainerProfile = new TrainerDTO.Response.TrainerProfile(trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),trainer.getSpecialization(),trainer.getUser().isActive(),trainees);
        log.debug("Mapped basic user info: firstName [{}], lastName [{}], active [{}].",
                trainerProfile.getFirstName(), trainerProfile.getFirstName(),
                trainerProfile.isActive());
        return trainerProfile;
    }
}
