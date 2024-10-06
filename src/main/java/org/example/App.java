package org.example;


import org.example.config.ProjectConfig;
import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.services.TrainerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;


public class App
{
    public static void main( String[] args ) {
        var context= new AnnotationConfigApplicationContext(ProjectConfig.class);
        TrainerService service=context.getBean(TrainerService.class);
        System.out.println(service.getAll());
        service.create("Abu","Barack",TrainingType.YOGA);
        System.out.println("______________");
        System.out.println(service.getAll());
//        service.create("Tim","Build",TrainingType.FITNESS);
//        System.out.println("______________");
//        System.out.println(service.getAll());
    }
}
