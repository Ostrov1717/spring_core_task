package org.example.services;

import org.example.dao.GenericDAO;
import org.example.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TraineeService {
    private final GenericDAO<Trainee> dao;
    private long nextId;

    @Autowired
    public TraineeService(GenericDAO<Trainee> dao) {
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);;
    }
    public void create(String firstName, String lastName, String address, LocalDate dateOfBirth){
        Trainee trainee=new Trainee();
        long id=++nextId;
        trainee.setUserId(id);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setActive(true);
        String userName=firstName+"."+lastName;
        long alingments=dao.getAll().values().stream()
                .filter(un->un.getFirstName().equals(firstName)&&un.getLastName().equals(lastName))
                .count();
        if(alingments>0){
            userName+=alingments;
        }
        trainee.setUsername(userName);
        String password=trainee.madePassword();
        trainee.setPassword(password);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        dao.save(trainee,id);
    }
    public Trainee selectById(long id){
        return dao.getAll().get(id);
    }
    public Trainee selectByUsername(String username){
        return getAll().stream().filter(el->el.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void update(String firstName, String lastName,  String userName, String address, LocalDate dateOfBirth ,boolean isActive){
        Trainee trainee=selectByUsername(userName);
        long id=trainee.getUserId();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setActive(isActive);
        dao.save(trainee,id);
    }
    public void delete(long id){
        dao.delete(id);
    }

    public List<Trainee> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }
}
