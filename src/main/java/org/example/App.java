package org.example;


import org.example.config.ProjectConfig;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.services.TraineeService;
import org.example.services.TrainerService;
import org.example.services.TrainingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;


public class App
{
    public static void main( String[] args ) {
        var context= new AnnotationConfigApplicationContext(ProjectConfig.class);
        TrainerService service=context.getBean(TrainerService.class);
        System.out.println(service.getAll());
        System.out.println("______________");
        TraineeService service1=context.getBean(TraineeService.class);
        System.out.println(service1.getAll());
        System.out.println("______________");
        TrainingService service2=context.getBean(TrainingService.class);
        System.out.println(service2.getAll());
//        service.create("Tim","Build",TrainingType.FITNESS);

//        System.out.println(service.getAll());
    }
}
