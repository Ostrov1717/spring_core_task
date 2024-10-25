package org.example.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;


@Service
@Slf4j
public class Facade {
//    private final TraineeService traineeService;
//    private final TrainerService trainerService;
//    private final TrainingService trainingService;
//
//    @Autowired
//    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
//        this.traineeService = traineeService;
//        this.trainerService = trainerService;
//        this.trainingService = trainingService;
//    }

//    public String getTrainingList(@NonNull String username, @NonNull LocalDateTime dataFrom, @NonNull LocalDateTime dataTo, boolean isTrainee) {
//        log.info("Fetching training list for {}: {}, from: {}, to: {}", isTrainee ? "trainee" : "trainer", username, dataFrom, dataTo);
//        Object user = isTrainee ?
//                traineeService.selectByUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainee " + username + " not found")) :
//                trainerService.selectByUsername(username).orElseThrow(() -> new IllegalArgumentException("Trainer " + username + "not found"));
//
//        List<Training> trainingList = getTrainings(dataFrom, dataTo, isTrainee, (isTrainee ? ((Trainee) user).getTraineeId() : ((Trainer) user).getTrainerId()));
//        String result = formatTrainingList(trainingList, user, isTrainee);
//        log.info("Successfully retrieved training list for {}: {}", isTrainee ? "trainee" : "trainer", username);
//        return result;
//    }
//
//    private List<Training> getTrainings(LocalDateTime dataFrom, LocalDateTime dataTo, boolean isTrainee, long userId) {
//        log.debug("Selecting trainings for {} within the period: {} - {}", isTrainee ? "trainee" : "trainer", dataFrom, dataTo);
//        return isTrainee ? trainingService.selectByTraineeId(userId) : trainingService.selectByTrainerId(userId)
//                .stream()
//                .filter(tr -> (isTrainee ? tr.getTrainee().getTraineeId() == userId : tr.getTrainer().getTrainerId() == userId))
//                .sorted(Comparator.comparing(Training::getTrainingDate))
//                .toList();
//    }
//
//    private String formatTrainingList(List<Training> trainings, Object user, boolean isTrainee) {
//        StringBuilder stringBuilder = new StringBuilder(String.format("Training list of %s - %s %s:\n",
//                isTrainee ? "trainee" : "trainer",
//                isTrainee ? ((Trainee) user).getUser().getFirstName() : ((Trainer) user).getUser().getFirstName(),
//                isTrainee ? ((Trainee) user).getUser().getLastName() : ((Trainer) user).getUser().getLastName()));
//
//        for (Training training : trainings) {
//            String duration = String.format("%02d:%02d:%02d", training.getTrainingDuration().toHours(),
//                    training.getTrainingDuration().toMinutesPart(),
//                    training.getTrainingDuration().toSecondsPart());
//            String userName = isTrainee ? trainerService.selectById(training.getTrainer().getTrainerId()).map(Trainer::getUser).map(User::getLastName).orElse("Unknown") :
//                    traineeService.selectById(training.getTrainee().getTraineeId()).map(Trainee::getUser).map(User::getLastName).orElse("Unknown");
//            stringBuilder.append(String.format("%s - Data and time: %s, duration: %s, training type: %s, %s's name: %s%n",
//                    training.getTrainingName(),
//                    training.getTrainingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                    duration,
//                    training.getTrainingType(),
//                    isTrainee ? "trainer" : "trainee",
//                    userName));
//        }
//        log.debug("Finished formatting training list for {}:", isTrainee ? "trainee" : "trainer");
//        return stringBuilder.toString();
//    }

}
