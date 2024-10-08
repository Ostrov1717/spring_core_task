package org.example.services;

import org.example.dao.GenericDAO;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {
    private final GenericDAO<Trainer> dao;
    private long nextId;

    @Autowired
    public TrainerService(GenericDAO<Trainer> dao) {
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);
    }
    public void create(String firstName, String lastName, TrainingType specialization){
        Trainer trainer=new Trainer();
        long id=++nextId;
        trainer.setUserId(id);
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
        dao.save(trainer,id);
    }
    public Trainer selectByUsername(String username){
        return getAll().stream().filter(el->el.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void update(String firstName, String lastName,  String userName, TrainingType specialization,boolean isActive){
        Trainer trainer=selectByUsername(userName);
        long id=trainer.getUserId();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setActive(isActive);
        dao.save(trainer,id);
    }
    public List<Trainer> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }
}
