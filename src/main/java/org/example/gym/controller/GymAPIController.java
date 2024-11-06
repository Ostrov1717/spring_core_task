package org.example.gym.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gym.dto.trainee.TraineeProfile;
import org.example.gym.dto.trainee.TraineeUpdateDTO;
import org.example.gym.dto.trainer.TrainerDTO;
import org.example.gym.dto.trainer.TrainerProfile;
import org.example.gym.dto.user.UserChangePasswordRequest;
import org.example.gym.dto.user.UserDTO;
import org.example.gym.dto.trainee.TraineeRequestDTO;
import org.example.gym.dto.trainer.TrainerRequestDTO;
import org.example.gym.dto.trainer.TrainerUpdateDTO;
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
    @PostMapping("/register")
    public UserDTO traineeRegistration(@Valid @RequestBody TraineeRequestDTO dto) {
        Trainee trainee = traineeService.create(dto.firstName(), dto.lastName(), dto.address(), parsingDate(dto.dateOfBirth()));
        return new UserDTO(trainee.getUser().getUsername(), trainee.getUser().getPassword());
    }

    //    2. Trainer Registration (POST method)
    @PostMapping("/register")
    public UserDTO trainerRegistration(@Valid @RequestBody TrainerRequestDTO dto) {
        Trainer trainer = trainerService.create(dto.firstName(), dto.lastName(), TrainingTypeName.valueOf((dto.specialization())));
        return new UserDTO(trainer.getUser().getUsername(), trainer.getUser().getPassword());
    }

    //  3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserDTO userData) {
        userService.authenticate(userData.username(), userData.password());
        return ResponseEntity.ok().build();
    }

    //    4. Change Login (PUT method)
    @PutMapping("/changeLogin")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody UserChangePasswordRequest request) {
        userService.changePassword(request.username(), request.oldPassword(), request.newPassword());
        return ResponseEntity.ok().build();
    }

    //  5. Get Trainee Profile (GET method
    @GetMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile getTraineeProfile(@Valid @RequestBody UserDTO userData) {
        return traineeService.findByUsername(userData.username(), userData.password());
    }

    //  6. Update Trainee Profile (PUT method)
    @PutMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile updateTraineeProfile(@Valid @RequestBody TraineeUpdateDTO dto) {
        return traineeService.update(dto.firstName(),
                dto.lastName(),
                dto.username(),
                dto.password(),
                dto.address(),
                parsingDate(dto.dateOfBirth()),
                Boolean.parseBoolean(dto.active()));
    }

    //  7. Delete Trainee Profile (DELETE method)
    @DeleteMapping
    public ResponseEntity<Void> deleteTraineeProfile(@Valid @RequestBody UserDTO userData) {
        traineeService.delete(userData.username(), userData.password());
        return ResponseEntity.ok().build();
    }

    //  8. Get Trainer Profile (GET method)
    @GetMapping(TRAINER_ENDPOINT + "/profile")
    public TrainerProfile getTrainerProfile(@Valid @RequestBody UserDTO userData) {
        return trainerService.findByUsername(userData.username(), userData.password());
    }

    //    9. Update Trainer Profile (PUT method)
    @PutMapping(TRAINER_ENDPOINT+"/update")
    public TrainerProfile updateTrainerProfile(@Valid @RequestBody TrainerUpdateDTO dto) {
        return trainerService.update(dto.firstName(),
                dto.lastName(),
                dto.username(),
                dto.password(),
                TrainingTypeName.valueOf(dto.specialization()),
                Boolean.parseBoolean(dto.active()));
    }

    //    10. Get not assigned on trainee active trainers. (GET method)
    @GetMapping(TRAINEE_ENDPOINT+"/notAssignTrainers")
    public ResponseEntity<Set<TrainerDTO>> getNotAssingTrainers(@Valid @RequestBody UserDTO userData){
        return ResponseEntity.ok(trainerService.getAvailableTrainers(userData.username(),userData.password()));
    }

    //    11. Update Trainee's Trainer List (PUT method)
    @PutMapping
    public ResponseEntity<Set<TrainerDTO>> updateTraineeTrainers(@RequestBody TraineeTrainersUpdateRequest request){
        Set
        return ResponseEntity.ok();
    }

//    12. Get Trainee Trainings List (GET method)




    private LocalDate parsingDate(String date) {
        LocalDate birthDate = null;
        if (date != null && !date.isEmpty()) {
            try {
                birthDate = LocalDate.parse(date); // Парсинг ISO-формата (YYYY-MM-DD)
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
            }
        }
        return birthDate;
    }
}
