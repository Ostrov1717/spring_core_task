package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ProjectConfig;
import org.example.model.Trainer;
import org.example.services.TraineeService;
import org.example.services.TrainerService;
import org.example.services.TrainingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Trainer> trainers=trainerService.getAvailableTrainers("Olga.Kurilenko");

        Set<Trainer> set=new HashSet<>();
        traineeService.updateTraineeTrainers("Kim.Johnson",set);
        System.out.println(trainers);


        entityManager.close();
        entityManagerFactory.close();

    }
}
