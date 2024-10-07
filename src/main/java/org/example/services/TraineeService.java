package org.example.services;

import org.example.dao.TraineeDAO;
import org.example.model.Trainee;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TraineeService {
    private final TraineeDAO dao;
    private long nextId;

    public TraineeService(TraineeDAO dao) {
        this.dao = dao;
        this.nextId = dao.getAll().keySet().stream().max(Long::compareTo).orElse(0L);;
    }
    public void create(String firstName, String lastName, String address, LocalDate dateOfBirth){
        Trainee trainee=new Trainee();
        trainee.setUserId(++nextId);
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
        dao.save(trainee);
    }
    public Trainee selectByUsername(String username){
        return dao.getAll().values().stream().filter(el->el.getUsername().equals(username)).findFirst().orElse(null);
    }
    public void update(String firstName, String lastName,  String userName, String address, LocalDate dateOfBirth ,boolean isActive){
        Trainee trainee=selectByUsername(userName);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setActive(isActive);
        dao.save(trainee);
    }
    public void delete(long id){
        dao.delete(id);
    }
    public List<Trainee> getAll(){
        return new ArrayList<>(dao.getAll().values());
    }
}
