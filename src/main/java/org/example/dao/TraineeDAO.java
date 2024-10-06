package org.example.dao;

import org.example.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TraineeDAO implements Dao<Trainee> {
    private InMemoryStorage storage;

    @Autowired
    public TraineeDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Trainee save(Trainee trainee) {
        storage.save(Trainee.class, trainee.getUserId(), trainee);
        return storage.findById(Trainee.class,trainee.getUserId());
    }

    public Trainee findById(Long id) {
        return storage.findById(Trainee.class, id);
    }

    @Override
    public Trainee update(Trainee entity) {
        storage.save(Trainee.class, entity.getUserId(), entity);
        return findById(entity.getUserId());
    }

    public void delete(Long id) {
        storage.delete(Trainee.class, id);
    }

    public Map<Long, Trainee> getAll() {
        return storage.getAll(Trainee.class);
    }
}
