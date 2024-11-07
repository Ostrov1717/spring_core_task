package org.example.gym.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gym.dto.trainee.TraineeDTO;
import org.example.gym.dto.trainee.TraineeMapper;
import org.example.gym.dto.trainer.TrainerDTO;
import org.example.gym.dto.trainer.TrainerMapper;
import org.example.gym.dto.user.UserDTO;
import org.example.gym.entity.Trainee;
import org.example.gym.entity.Trainer;
import org.example.gym.entity.TrainingTypeName;
import org.example.gym.service.TraineeService;
import org.example.gym.service.TrainerService;
import org.example.gym.service.TrainingService;
import org.example.gym.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class GymAPIController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    private final TrainingService trainingService;
    private final UserService userService;

    public static final String BASE_URL = "";
    public static final String TRAINEE_ENDPOINT = "/trainee";
    public static final String TRAINER_ENDPOINT = "/trainer";

    //    1. Trainee Registration (POST method)
    @PostMapping(TRAINEE_ENDPOINT+"/register")
    public ResponseEntity<UserDTO.Response.Login> traineeRegistration(@Valid @RequestBody TraineeDTO.Request.Create dto) {
        Trainee trainee = traineeService.create(dto.getFirstName(), dto.getLastName(), dto.getAddress(), dto.getDateOfBirth());
        return ResponseEntity.ok(new UserDTO.Response.Login(trainee.getUser().getUsername(), trainee.getUser().getPassword()));
    }

    //    2. Trainer Registration (POST method)
    @PostMapping(TRAINER_ENDPOINT+"/register")
    public ResponseEntity<UserDTO.Response.Login> trainerRegistration(@Valid @RequestBody TrainerDTO.Request.Create dto) {
        Trainer trainer = trainerService.create(dto.getFirstName(), dto.getLastName(),
                TrainingTypeName.valueOf(dto.getSpecialization().getTrainingType()));
        return ResponseEntity.ok(new UserDTO.Response.Login(trainer.getUser().getUsername(), trainer.getUser().getPassword()));
    }

    //  3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserDTO.Request.Login dto) {
        userService.authenticate(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok().build();
    }

    //    4. Change Login (PUT method)
    @PutMapping("/changeLogin")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody UserDTO.Request.ChangeLogin dto ) {
        userService.changePassword(dto.getUsername(), dto.getPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    //  5. Get Trainee Profile (GET method
    @GetMapping(TRAINEE_ENDPOINT + "/profile")
    public ResponseEntity<TraineeDTO.Response.TraineeProfile> getTraineeProfile(@Valid @RequestBody UserDTO.Request.Login dto) {
        Trainee trainee=traineeService.findByUsername(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(TraineeMapper.toProfile(trainee));
    }

    //  6. Update Trainee Profile (PUT method)
    @PutMapping(TRAINEE_ENDPOINT + "/profile")
    public ResponseEntity<TraineeDTO.Response.TraineeProfileFull> updateTraineeProfile(@Valid @RequestBody TraineeDTO.Request.Update dto) {
        Trainee trainee=traineeService.update(dto.getFirstName(), dto.getLastName(), dto.getUsername(),
                dto.getPassword(), dto.getAddress(), dto.getDateOfBirth(), dto.isActive());
        return ResponseEntity.ok(TraineeMapper.toProfileFull(trainee));
    }

    //  7. Delete Trainee Profile (DELETE method)
    @DeleteMapping(TRAINEE_ENDPOINT + "/delete")
    public ResponseEntity<Void> deleteTraineeProfile(@Valid @RequestBody UserDTO.Request.Login dto) {
        traineeService.delete(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok().build();
    }

    //  8. Get Trainer Profile (GET method)
    @GetMapping(TRAINER_ENDPOINT + "/profile")
    public ResponseEntity<TrainerDTO.Response.TrainerProfile> getTrainerProfile(@Valid @RequestBody UserDTO.Request.Login dto) {
        Trainer trainer=trainerService.findByUsername(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(TrainerMapper.toProfile(trainer));
    }

    //    9. Update Trainer Profile (PUT method)
    @PutMapping(TRAINER_ENDPOINT+"/update")
    public ResponseEntity<TrainerDTO.Response.TrainerProfile> updateTrainerProfile(@Valid @RequestBody TrainerDTO.Request.Update dto) {
        Trainer trainer=trainerService.update(dto.getFirstName(), dto.getLastName(), dto.getUsername(),dto.getPassword(),
                TrainingTypeName.valueOf(dto.getSpecialization().getTrainingType()), dto.isActive());
        return ResponseEntity.ok(TrainerMapper.toProfile(trainer));
    }

    //    10. Get not assigned on trainee active trainers. (GET method)
    @GetMapping(TRAINEE_ENDPOINT+"/notAssignTrainers")
    public ResponseEntity<Set<TrainerDTO.Response.TrainerSummury>> getNotAssingTrainers(@Valid @RequestBody UserDTO.Request.Login dto){
       Set<Trainer> trainers=trainerService.getAvailableTrainers(dto.getUsername(), dto.getPassword());
       return ResponseEntity.ok(TrainerMapper.toSetTrainerSummury(trainers));
    }

    //    11. Update Trainee's Trainer List (PUT method)
    @PutMapping
    public ResponseEntity<Set<TrainerDTO.Response.TrainerSummury>> updateTraineeTrainers(@Valid @RequestBody TraineeDTO.Request.UpdateTrainers dto){
        Set<Trainer> newTrainers=new HashSet<>();
        for (TrainerDTO.Response.TrainerUsername usernameDTO: dto.getTrainersUsernames()) {
            newTrainers.add(trainerService.findTrainerByUsername(usernameDTO.getUsername()));
        }
        Set<Trainer> trainers=traineeService.updateTraineeTrainers(dto.getUsername(),dto.getPassword(),newTrainers);
        return ResponseEntity.ok(TrainerMapper.toSetTrainerSummury(trainers));
    }

//    12. Get Trainee Trainings List (GET method)

}
