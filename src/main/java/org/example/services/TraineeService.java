package org.example.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.TraineeRepository;
import org.example.model.Trainee;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class TraineeService {
    private TraineeRepository dao;

    @Autowired
    public void setDao(TraineeRepository dao) {
        this.dao = dao;
    }

    public Optional<Trainee> create(@NonNull String firstName, @NonNull String lastName, String address, LocalDate dateOfBirth) {
        log.info("Creation of new Trainee: {} {}", firstName, lastName);
        if (firstName.isBlank() || lastName.isBlank()) {
            log.error("Impossible to create new Trainee: blank firstname or lastname");
            throw new IllegalArgumentException("Trainee without firstname and lastname cannot be created !");
        }
        String userName = getUsername(firstName, lastName);
        String password = madePassword();
        Trainee trainee = new Trainee(firstName, lastName, userName, password, true, address, dateOfBirth);
        log.info("Trainee has been created with Id: {}, username: {}", null, userName);
        return Optional.of(dao.save(trainee));
    }

    public Optional<Trainee> selectById(long id) {
        log.info("Search Trainee by Id: {}", id);
        return dao.findById(id);
    }

    public Optional<Trainee> selectByUsername(String username) {
        log.info("Search Trainee by username: {}", username);
        return getAll().stream().filter(el -> el.getUser().getUsername().equals(username)).findFirst();
    }

    public void update(String firstName, String lastName, String userName, String address, LocalDate dateOfBirth, boolean isActive) throws IllegalArgumentException {
        log.info("Updating Trainee's data with username: {}", userName);
        Trainee trainee = selectByUsername(userName).orElseThrow(() -> new IllegalArgumentException("Trainee with username: " + userName + " not found."));
        trainee.getUser().setFirstName(firstName);
        trainee.getUser().setLastName(lastName);
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.getUser().setActive(isActive);
        log.info("Trainee's data with username: {} has been updated", userName);
    }

    public void delete(String userName) {
        log.info("Deleting Trainee with username: {}", userName);
        Trainee trainee = selectByUsername(userName).orElseThrow(() -> new IllegalArgumentException("Trainee with username: " + userName + " not found."));
        log.info("Trainee with username: {} successfully deleted", userName);
        dao.delete(trainee);
    }

    public List<Trainee> getAll() {
        log.info("Getting all Trainees");
        return dao.findAll();
    }

    private String getUsername(String firstName, String lastName) {
        String userName = firstName + "." + lastName;
        long alingments = dao.findAll().stream()
                .filter(un -> un.getUser().getFirstName().equals(firstName) && un.getUser().getLastName().equals(lastName))
                .count();
        if (alingments > 0) {
            userName += alingments;
        }
        return userName;
    }

    private String madePassword() {
        Random random = new Random();
        return Stream.generate(() -> (char) random.nextInt(33, 122))
                .filter(Character::isLetter)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
