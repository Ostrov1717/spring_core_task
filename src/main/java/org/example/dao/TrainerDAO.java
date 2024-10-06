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

    @PostConstruct
    public void init(){
            System.out.println("init methods");
            storage.save(Trainer.class,1L, new Trainer(1L, "Tim", "Wallace", "tim.wallace", "1234", true, TrainingType.YOGA));
            storage.save(Trainer.class,2L,new Trainer(2L, "Tom", "Robins", "tom.robins", "1234", true, TrainingType.FITNESS));
            storage.save(Trainer.class,3L, new Trainer(3L, "Bob", "Getty", "bob.getty", "1234", true, TrainingType.STRETCHING));
            storage.save(Trainer.class,4L,new Trainer(4L, "Mary", "Popins", "mary.popins", "1234", true, TrainingType.RESISTANCE));
            storage.save(Trainer.class,5L,new Trainer(5L, "Jack", "Daniels", "jack.daniels", "1234", true, TrainingType.YOGA));
        }
}
