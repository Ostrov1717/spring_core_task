package org.example.profiles;

import org.example.model.Trainer;
public class TrainerMapper {
    public static TrainerProfile toProfile(Trainer trainer){
        if(trainer==null){
            return null;
        }
        TrainerProfile trainerProfile=new TrainerProfile();
        trainerProfile.setFirstName(trainer.getUser().getFirstName());
        trainerProfile.setLastName(trainer.getUser().getLastName());
        trainerProfile.setUsername(trainer.getUser().getUsername());
        trainerProfile.setActive(trainer.getUser().isActive());
        trainerProfile.setSpecialization(trainer.getSpecialization());
//        trainerProfile.setTrainees(trainer.getTrainees());
//        trainerProfile.setTrainings(trainer.getTrainings());
        return trainerProfile;
    }
//    Trainer toTrainer(TrainerProfile trainerProfile);
}
