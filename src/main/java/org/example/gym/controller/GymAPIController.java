package org.example.gym.controller;

import lombok.RequiredArgsConstructor;
import org.example.gym.dto.TraineeProfile;
import org.example.gym.dto.TrainerDTO;
import org.example.gym.dto.TrainerProfile;
import org.example.gym.dto.UserDTO;
import org.example.gym.entity.Trainee;
import org.example.gym.entity.Trainer;
import org.example.gym.entity.TrainingType;
import org.example.gym.entity.TrainingTypeName;
import org.example.gym.service.TraineeService;
import org.example.gym.service.TrainerService;
import org.example.gym.service.TrainingService;
import org.example.gym.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
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
    public UserDTO traineeRegistration(@RequestParam String firstName,
                                       @RequestParam String lastName,
                                       @RequestParam(required = false) String dateOfBirth,
                                       @RequestParam(required = false) String address) {
        Trainee trainee = traineeService.create(firstName, lastName, address, parsingDate(dateOfBirth));
        return new UserDTO(trainee.getUser().getUsername(), trainee.getUser().getPassword());
    }

    //    2. Trainer Registration (POST method)
    @PostMapping("/register")
    public UserDTO trainerRegistration(@RequestParam String firstName,
                                       @RequestParam String lastName,
                                       @RequestParam String specialization) {
        Trainer trainer = trainerService.create(firstName, lastName, TrainingTypeName.valueOf((specialization));
        return new UserDTO(trainer.getUser().getUsername(), trainer.getUser().getPassword());
    }

    //  3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.authenticate(username, password);
        return ResponseEntity.ok().build();
    }

    //    4. Change Login (PUT method)
    @PutMapping("/changeLogin")
    public ResponseEntity<Void> changeLogin(@RequestParam("username") String username,
                                            @RequestParam("oldPassword") String oldPassword,
                                            @RequestParam("newPassword") String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    //  5. Get Trainee Profile (GET method
    @GetMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile getTraineeProfile(@RequestParam("username") String username,
                                            @RequestParam("password") String password) {
        return traineeService.findByUsername(username, password);
    }

    //  6. Update Trainee Profile (PUT method)
    @PutMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile updateTraineeProfile(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam(required = false) String dateOfBirth,
                                               @RequestParam(required = false) String address,
                                               @RequestParam boolean active
                                               ) {
        return traineeService.update(firstName, lastName, username, password, address, parsingDate(dateOfBirth), active);
    }


    //  7. Delete Trainee Profile (DELETE method)
    @DeleteMapping
    public ResponseEntity<Void> deleteTraineeProfile(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        traineeService.delete(username, password);
        return ResponseEntity.ok().build();
    }

    //  8. Get Trainer Profile (GET method)
    @GetMapping(TRAINER_ENDPOINT + "/profile")
    public TrainerProfile getTrainerProfile(@RequestParam("username") String username,
                                            @RequestParam("password") String password) {
        return trainerService.findByUsername(username, password);
    }

    //    9. Update Trainer Profile (PUT method)
    @PutMapping(TRAINER_ENDPOINT+"/update")
    public TrainerProfile updateTrainerProfile(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam(required = false) String specialization,
                                               @RequestParam boolean active) {
        return trainerService.update(firstName, lastName, username, password, TrainingTypeName.valueOf(specialization), active);
    }

//    10. Get not assigned on trainee active trainers. (GET method)
    @GetMapping(TRAINEE_ENDPOINT+"/notAssignTrainers")
    public ResponseEntity<Set<TrainerDTO>> getNotAssingTrainers(@RequestParam("username") String username,
                                                                @RequestParam("password") String password){
        return ResponseEntity.ok(trainerService.getAvailableTrainers(username));
    }

//    11. Update Trainee's Trainer List (PUT method)
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
