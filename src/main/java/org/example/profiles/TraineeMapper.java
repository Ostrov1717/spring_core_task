package org.example.profiles;

import org.example.model.Trainee;

public class TraineeMapper {

    public static TraineeProfile toProfile(Trainee trainee){
        if(trainee==null) {
            return null;
        }
        TraineeProfile traineeProfile=new TraineeProfile();
        traineeProfile.setFirstName(trainee.getUser().getFirstName());
        traineeProfile.setLastName(trainee.getUser().getLastName());
        traineeProfile.setUsername(trainee.getUser().getUsername());
        traineeProfile.setActive(trainee.getUser().isActive());
        traineeProfile.setAddress(trainee.getAddress());
        traineeProfile.setDateOfBirth(trainee.getDateOfBirth());
//        traineeProfile.setTrainers(trainee.getTrainers());
//        traineeProfile.setTrainings(trainee.getTrainings());
        return traineeProfile;
    };

//    Trainee traineeProfileToTrainee(TraineeProfile traineeProfile);
}
