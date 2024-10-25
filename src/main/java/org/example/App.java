package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ProjectConfig;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.enums.TrainingTypeName;
import org.example.services.TraineeService;
import org.example.services.TrainerService;
import org.example.services.TrainingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
public class App {
    @PersistenceContext
    EntityManager entityManager;
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("default");
        EntityManager entityManager=entityManagerFactory.createEntityManager();


        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        TrainerService trainerService=context.getBean(TrainerService.class);
        TraineeService traineeService=context.getBean(TraineeService.class);
        TrainingService trainingService=context.getBean(TrainingService.class);

        trainerService.create("Helen","Doron", TrainingTypeName.ZUMBA);
        trainerService.create("Monica","Dobs",TrainingTypeName.FITNESS);
        trainerService.create("Wallace", "Tim",TrainingTypeName.YOGA);
        trainerService.create("Tom", "Robins",TrainingTypeName.FITNESS);
        trainerService.create("Bob", "Getty",TrainingTypeName.STRETCHING);
        trainerService.create("Mary", "Popins",TrainingTypeName.RESISTANCE);
        trainerService.create("Jack", "Daniels",TrainingTypeName.YOGA);

        traineeService.create("Olga","Kurilenko","California", LocalDate.parse("1989-10-05"));
        traineeService.create("Kim","Johnson","Chicago", LocalDate.parse("1986-12-30"));
        traineeService.create("Tomas","Kuk","Sweden Oslo", LocalDate.parse("1972-02-01"));
        traineeService.create("George","TheThird","UK", LocalDate.parse("1962-05-05"));




        entityManager.close();
        entityManagerFactory.close();

//        Facade facade = context.getBean(Facade.class);
//        LocalDateTime dataFrom = LocalDateTime.of(2024, 10, 1, 1, 0);
//        LocalDateTime dataTo = LocalDateTime.of(2024, 10, 10, 1, 0);
//        log.info(facade.getTrainingList("Kim.Johnson", dataFrom, dataTo,true));
//        log.info(facade.getTrainingList("Tim.Wallace", dataFrom, dataTo,false));
    }
}
