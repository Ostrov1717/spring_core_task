package org.example.services;

import org.example.dao.TrainingDAO;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingDAO dao;

    private long nextId;

    public TrainingService(TrainingDAO dao){
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);
    }
    public void create(long traineeId, long trainerId, String trainingName, TrainingType type, LocalDateTime trainingDate, Duration duration){
        Training training=new Training();
        training.setTrainingId(++nextId);
        training.setTraineeId(traineeId);
        training.setTrainerId(trainerId);
        training.setTrainingName(trainingName);
        training.setTrainingType(type);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(duration);
        dao.save(training);
    }

    public Training selectByTraineeId(long traineeId){
        return null;
    }

    public List<Training> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }


}
