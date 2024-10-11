package org.example;

import org.example.config.ProjectConfig;
import org.example.model.TrainingType;
import org.example.services.Facade;
import org.example.services.TraineeService;
import org.example.services.TrainerService;
import org.example.services.TrainingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class App
{
    public static void main( String[] args ) {
        var context= new AnnotationConfigApplicationContext(ProjectConfig.class);
//        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        TrainerService service=context.getBean(TrainerService.class);
//        System.out.println(service.getAll());
        System.out.println("______________");
        TraineeService service1=context.getBean(TraineeService.class);

        TrainingService service2=context.getBean(TrainingService.class);
        System.out.println(service2.getAll());
//        Facade facade=context.getBean(Facade.class);
//        System.out.println(facade.getTraineeTrainingList("tomas.kuk",null,null));

//        service.create("Tim","Build", TrainingType.YOGA);
//        service.update("Tima","Builda","Tim.Build", TrainingType.STRETCHING,false);
//        service1.create("Aby","Barrack","Rome", LocalDate.parse("1995-01-12"));
//        System.out.println(service.selectById(6));
//        System.out.println("______________");
//        service1.update("Abyda","Barracka","Aby.Barrack","Seville", LocalDate.parse("1995-01-12"),false);
//        System.out.println(service1.selectById(5));
//        System.out.println("*********************");
//        service1.update("Olga","Cruze","Olga.Kurilenko","Viena", LocalDate.of(1989,10,15),false);
//
////        System.out.println("++++++++++++++++++++");
//        System.out.println(service.getAll());
////        System.out.println("______________");
////
//        System.out.println(service.selectByUsername("Bob.Getty"));
//        System.out.println(service1.selectByUsername("tomas.kuk"));
//
//        service2.create(1l,2l,"Outside workout",TrainingType.FITNESS, LocalDateTime.now(), Duration.parse("PT1H30M"));
//        System.out.println(service2.getAll());
//        System.out.println("______________");
    }
}
