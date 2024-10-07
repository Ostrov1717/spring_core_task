package org.example.services;

import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainerService {
    private final TrainerDAO dao;
    private long nextId;

    public TrainerService(TrainerDAO dao) {
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);
    }
    public void create(String firstName, String lastName, TrainingType specialization){
        Trainer trainer=new Trainer();
        trainer.setUserId(++nextId);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setActive(true);

        String userName=firstName+"."+lastName;
        long alingments=dao.getAll().values().stream()
                .filter(un->un.getFirstName().equals(firstName)&&un.getLastName().equals(lastName))
                .count();
        if(alingments>0){
           userName+=alingments;
        }
        trainer.setUsername(userName);
        String password=trainer.madePassword();
        trainer.setPassword(password);
        trainer.setSpecialization(specialization);
        dao.save(trainer);
    }
    public Trainer selectByUsername(String username){
        Trainer trainer=dao.getAll().values().stream().filter(el->el.getUsername().equals(username)).findFirst().orElse(null);
        return trainer;
    }

    public void update(String firstName, String lastName,  String userName, TrainingType specialization,boolean isActive){
        Trainer trainer=selectByUsername(userName);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setActive(isActive);
        dao.save(trainer);
    }
    public List<Trainer> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }
}
