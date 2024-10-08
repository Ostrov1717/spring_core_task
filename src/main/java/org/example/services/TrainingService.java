package org.example.services;

import org.example.dao.GenericDAO;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {

    private final GenericDAO<Training> dao;

    private long nextId;

    @Autowired
    public TrainingService(GenericDAO<Training> dao){
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);
    }
    public void create(long traineeId, long trainerId, String trainingName, TrainingType type, LocalDateTime trainingDate, Duration duration){
        Training training=new Training();
        long id=++nextId;
        training.setTrainingId(id);
        training.setTraineeId(traineeId);
        training.setTrainerId(trainerId);
        training.setTrainingName(trainingName);
        training.setTrainingType(type);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(duration);
        dao.save(training,id);
    }

    public Training selectByTraineeId(long traineeId){
        return null;
    }

    public List<Training> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }


}
