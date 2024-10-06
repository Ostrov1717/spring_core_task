package org.example.dao;

import org.example.model.Training;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainingDAO implements Dao<Training>{
    private InMemoryStorage storage;

    public TrainingDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Training save(Training training) {
        storage.save(Training.class, training.getTraineeId(), training);
        return storage.findById(Training.class,training.getTrainingId());
    }

    public Training findById(Long id) {
        return storage.findById(Training.class, id);
    }

    @Override
    public Training update(Training entity) {
        storage.save(Training.class, entity.getTrainingId(), entity);
        return storage.findById(Training.class, entity.getTrainingId());
    }

    public void delete(Long id) {
        storage.delete(Training.class, id);
    }

    public Map<Long, Training> getAll() {
        return storage.getAll(Training.class);
    }
}
