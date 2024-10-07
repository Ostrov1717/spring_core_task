package org.example.dao;

import jakarta.annotation.PostConstruct;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainerDAO implements Dao<Trainer> {
        private InMemoryStorage storage;

        @Autowired
        public TrainerDAO(InMemoryStorage storage) {
            this.storage = storage;
        }

        public Trainer save(Trainer trainer) {
            storage.save(Trainer.class, trainer.getUserId(), trainer);
            return storage.findById(Trainer.class,trainer.getUserId());
        }

        public Trainer findById(Long id) {
            return storage.findById(Trainer.class, id);
        }

        public void delete(Long id) {
            storage.delete(Trainer.class, id);
        }
        public Map<Long,Trainer> getAll() {
            return storage.getAll(Trainer.class);
        }


    @Override
    public Trainer update(Trainer entity) {
       storage.save(Trainer.class,entity.getUserId(),entity);
       return findById(entity.getUserId());
    }

}
