package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ProjectConfig;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.enums.TrainingTypeName;
import org.example.services.TrainerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
//        trainerService.create("Helen","Doron", TrainingTypeName.ZUMBA);
//        trainerService.create("Monica","Dobs",TrainingTypeName.FITNESS);
//
//        trainerService.create("Wallace", "Tim",TrainingTypeName.YOGA);
//        trainerService.create("Tom", "Robins",TrainingTypeName.FITNESS);
//        trainerService.create("Bob", "Getty",TrainingTypeName.STRETCHING);
//        trainerService.create("Mary", "Popins",TrainingTypeName.RESISTANCE);
//        trainerService.create("Jack", "Daniels",TrainingTypeName.YOGA);
//        trainerService.create("Jack", "Daniels",TrainingTypeName.RESISTANCE);
        System.out.println(trainerService.selectById(1L));
//        System.out.println(trainerService.deActivate("Helen.Doron","1"));
        System.out.println(trainerService.activate("Helen.Doron","1"));




        entityManager.close();
        entityManagerFactory.close();

//        Facade facade = context.getBean(Facade.class);
//        LocalDateTime dataFrom = LocalDateTime.of(2024, 10, 1, 1, 0);
//        LocalDateTime dataTo = LocalDateTime.of(2024, 10, 10, 1, 0);
//        log.info(facade.getTrainingList("Kim.Johnson", dataFrom, dataTo,true));
//        log.info(facade.getTrainingList("Tim.Wallace", dataFrom, dataTo,false));
    }
}
