package org.example.gym.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gym.dto.trainee.TraineeDTO;
import org.example.gym.dto.trainer.TrainerDTO;
import org.example.gym.dto.training.TrainingDTO;
import org.example.gym.dto.user.UserDTO;
import org.example.gym.entity.Trainer;
import org.example.gym.entity.TrainingType;
import org.example.gym.entity.TrainingTypeName;
import org.example.gym.service.TraineeService;
import org.example.gym.service.TrainerService;
import org.example.gym.service.TrainingService;
import org.example.gym.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class GymAPIController {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;

    public static final String BASE_URL = "gym";
    public static final String TRAINEE_ENDPOINT = "trainee";
    public static final String TRAINER_ENDPOINT = "trainer";

    //    1. Trainee Registration (POST method)
    @PostMapping(TRAINEE_ENDPOINT + "/register")
    public ResponseEntity<UserDTO.Response.Login> traineeRegistration(@Valid @RequestBody TraineeDTO.Request.Create dto) {
        log.info("POST request to " + TRAINEE_ENDPOINT + "/register for registration trainee with details: {} {}, date of birth={}, address={}"
                , dto.getFirstName(), dto.getLastName(), dto.getDateOfBirth(), dto.getAddress());
        return ResponseEntity.ok(traineeService.create(dto.getFirstName(), dto.getLastName(), dto.getAddress(), dto.getDateOfBirth()));
    }

    //    2. Trainer Registration (POST method)
    @PostMapping(TRAINER_ENDPOINT + "/register")
    public ResponseEntity<UserDTO.Response.Login> trainerRegistration(@Valid @RequestBody TrainerDTO.Request.Create dto) {
        log.info("POST request to " + TRAINER_ENDPOINT + "/register for registration trainer with details: {} {}, specialization={}"
                , dto.getFirstName(), dto.getLastName(), dto.getSpecialization());
        return ResponseEntity.ok(trainerService.create(dto.getFirstName(), dto.getLastName(),
                TrainingTypeName.valueOf(dto.getSpecialization().getTrainingType())));
    }

    //  3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<Void> login(@Valid @ModelAttribute UserDTO.Request.Login dto) {
        log.info("GET request to /login with username={}", dto.getUsername());
        userService.authenticate(dto.getUsername(), dto.getPassword());
        log.info("GET request successful. Username authenticated.");
        return ResponseEntity.ok().build();
    }

    //    4. Change Login (PUT method)
    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody UserDTO.Request.ChangeLogin dto) {
        log.info("PUT request to /change-login username={} with new password.", dto.getUsername());
        userService.changePassword(dto.getUsername(), dto.getPassword(), dto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    //  5. Get Trainee Profile (GET method
    @GetMapping(TRAINEE_ENDPOINT + "/profile")
    public ResponseEntity<TraineeDTO.Response.TraineeProfile> getTraineeProfile(@Valid @ModelAttribute UserDTO.Request.Login dto) {
        log.info("GET request to " + TRAINEE_ENDPOINT + "/profile with trainee username={}", dto.getUsername());
        return ResponseEntity.ok(traineeService.findByUsername(dto.getUsername(), dto.getPassword()));
    }

    //  6. Update Trainee Profile (PUT method)
    @PutMapping(TRAINEE_ENDPOINT + "/update")
    public ResponseEntity<TraineeDTO.Response.TraineeProfileFull> updateTraineeProfile(@Valid @RequestBody TraineeDTO.Request.Update dto) {
        log.info("PUT request to " + TRAINEE_ENDPOINT + "/update trainee={} with new details: {} {}, date of birth={}, address={}",
                dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getAddress(), dto.getDateOfBirth());
        return ResponseEntity.ok(traineeService.update(dto.getFirstName(), dto.getLastName(), dto.getUsername(),
                dto.getPassword(), dto.getAddress(), dto.getDateOfBirth(), dto.isActive()));
    }

    //  7. Delete Trainee Profile (DELETE method)
    @DeleteMapping(TRAINEE_ENDPOINT + "/delete")
    public ResponseEntity<Void> deleteTraineeProfile(@Valid @RequestBody UserDTO.Request.Login dto) {
        log.info("DELETE request to "+TRAINEE_ENDPOINT+"/delete trainee={}", dto.getUsername());
        traineeService.delete(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok().build();
    }

    //  8. Get Trainer Profile (GET method)
    @GetMapping(TRAINER_ENDPOINT + "/profile")
    public ResponseEntity<TrainerDTO.Response.TrainerProfile> getTrainerProfile(@Valid @ModelAttribute UserDTO.Request.Login dto) {
        log.info("GET request to " + TRAINER_ENDPOINT + "/profile with trainer username={}", dto.getUsername());
        return ResponseEntity.ok(trainerService.findByUsername(dto.getUsername(), dto.getPassword()));
    }

    //    9. Update Trainer Profile (PUT method)
    @PutMapping(TRAINER_ENDPOINT + "/update")
    public ResponseEntity<TrainerDTO.Response.TrainerProfile> updateTrainerProfile(@Valid @RequestBody TrainerDTO.Request.Update dto) {
        log.info("PUT request to " + TRAINER_ENDPOINT + "/update trainer={} with new details: {} {}, specialization={}",
                dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getSpecialization());
        return ResponseEntity.ok(trainerService.update(dto.getFirstName(), dto.getLastName(), dto.getUsername(), dto.getPassword(),
                TrainingTypeName.valueOf(dto.getSpecialization().getTrainingType()), dto.isActive()));
    }

    //    10. Get not assigned on trainee active trainers. (GET method)
    @GetMapping(TRAINEE_ENDPOINT + "/not-assign-trainers")
    public ResponseEntity<Set<TrainerDTO.Response.TrainerSummury>> getNotAssingTrainers(@Valid @ModelAttribute UserDTO.Request.Login dto) {
        log.info("GET request to " + TRAINEE_ENDPOINT + "/not-assign-trainers from username={}", dto.getUsername());
        return ResponseEntity.ok(trainerService.getAvailableTrainers(dto.getUsername(), dto.getPassword()));
    }

    //    11. Update Trainee's Trainer List (PUT method)
    @PutMapping(TRAINEE_ENDPOINT + "/update-trainers")
    public ResponseEntity<Set<TrainerDTO.Response.TrainerSummury>> updateTraineeTrainers(@Valid @RequestBody TraineeDTO.Request.UpdateTrainers dto) {
        log.info("PUT request to " +TRAINEE_ENDPOINT +"/update-trainers for trainee={} with new trainers: {}",
                dto.getUsername(), dto.getTrainersUsernames());
        Set<Trainer> newTrainers = trainerService.getTrainerFromList(dto.getTrainersUsernames());
        return ResponseEntity.ok(traineeService.updateTraineeTrainers(dto.getUsername(), dto.getPassword(), newTrainers));
    }

    //    12. Get Trainee Trainings List (GET method)
    @GetMapping(TRAINEE_ENDPOINT + "/trainings")
    public ResponseEntity<List<TrainingDTO.Response.TrainingProfileForTrainee>> getTraineeTrainings(@Valid @RequestBody TrainingDTO.Request.TraineeTrainings dto) {
        log.info("GET request to " + TRAINEE_ENDPOINT + "/trainings for trainee with username={}, period from {} to {}, trainer={}",
                dto.getTraineeUsername(), dto.getPeriodFrom(), dto.getPeriodTo(), dto.getTrainerUsername());
        return ResponseEntity.ok(trainingService.findTraineeList(dto.getTraineeUsername(), dto.getPeriodFrom(), dto.getPeriodTo(), dto.getTrainerUsername(),
                String.valueOf(dto.getTrainingType())));
    }

    //  13. Get Trainer Trainings List (GET method)
    @GetMapping(TRAINER_ENDPOINT + "/trainings")
    public ResponseEntity<List<TrainingDTO.Response.TrainingProfileForTrainer>> getTrainerTrainings(@Valid @RequestBody TrainingDTO.Request.TrainerTrainings dto) {
        log.info("GET request to " + TRAINER_ENDPOINT + "/trainings for trainer with username={}, period from {} to {}, trainee={}",
                dto.getTrainerUsername(), dto.getPeriodFrom(), dto.getPeriodTo(), dto.getTraineeUsername());
        return ResponseEntity.ok(trainingService.findTrainerList(dto.getTrainerUsername(), dto.getPeriodFrom(), dto.getPeriodTo(), dto.getTraineeUsername()));
    }

    //    14. Add Training (POST method)
    @PostMapping("/create-training")
    public ResponseEntity<Void> createTraining(@Valid @RequestBody TrainingDTO.Request.Create dto) {
        log.info("POST request to /create-training for creating training for trainee={} with details: {}, {}, {}, trainer name={}",
                dto.getTraineeUsername(),dto.getTrainingName(),dto.getTrainingDate(),dto.getTrainingDuration(),dto.getTrainerUsername());
        trainingService.create(dto.getTraineeUsername(), dto.getTrainerUsername(), dto.getTrainingName(), dto.getTrainingDate(), dto.getTrainingDuration());
        return ResponseEntity.ok().build();
    }

    //    15. Activate/De-Activate Trainee (PATCH method)
    @PatchMapping(TRAINEE_ENDPOINT + "/status")
    public ResponseEntity<Void> activateOrDeactivateTrainee(@Valid @RequestBody UserDTO.Request.ActivateOrDeactivate dto) {
        log.info("PATCH request to "+TRAINEE_ENDPOINT+"/status trainee={} with updates active status on: {}", dto.getUsername(),dto.isActive());
        if (dto.isActive()) {
            userService.activate(dto.getUsername(), dto.getPassword());
        } else {
            userService.deactivate(dto.getUsername(), dto.getPassword());
        }
        return ResponseEntity.ok().build();
    }

    //    16. Activate/De-Activate Trainer (PATCH method)
    @PatchMapping(TRAINER_ENDPOINT + "/status")
    public ResponseEntity<Void> activateOrDeactivateTrainer(@Valid @RequestBody UserDTO.Request.ActivateOrDeactivate dto) {
        log.info("PATCH request to "+TRAINER_ENDPOINT+"/status trainer={} with updates active status on: {}", dto.getUsername(),dto.isActive());
        if (dto.isActive()) {
            userService.activate(dto.getUsername(), dto.getPassword());
        } else {
            userService.deactivate(dto.getUsername(), dto.getPassword());
        }
        return ResponseEntity.ok().build();
    }

    //  17. Get Training types (GET method)
    @GetMapping("/types")
    public List<TrainingType> getTrainingTypes() {
        log.info("GET request to /types for training types in gym");
        return trainerService.trainingTypes();
    }
}
