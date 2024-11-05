package org.example.gym.controller;

import org.example.gym.dto.TraineeProfile;
import org.example.gym.dto.UserDTO;
import org.example.gym.entity.Trainee;
import org.example.gym.service.TraineeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
public class TraineeController {
    private final TraineeService traineeService;

    public static final String BASE_URL = "";
    public static final String TRAINEE_ENDPOINT = "/trainee";

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping("/register")
    public UserDTO traineeRegistration(@RequestParam String firstName,
                                       @RequestParam String lastName,
                                       @RequestParam(required = false) String dateOfBirth,
                                       @RequestParam(required = false) String address) {

        Trainee trainee = traineeService.create(firstName, lastName, address, parsingData(dateOfBirth));
        return new UserDTO(trainee.getUser().getUsername(), trainee.getUser().getPassword());
    }

    @GetMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile getTraineeProfile(@RequestParam("username") String username, @RequestParam("password") String password) {
        return traineeService.findByUsername(username, password);
    }

    @PutMapping(TRAINEE_ENDPOINT + "/profile")
    public TraineeProfile updateTraineeProfile(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam(required = false) String dateOfBirth,
                                               @RequestParam(required = false) String address,
                                               @RequestParam boolean active) {
        return traineeService.update(firstName, lastName, username, password, address, parsingData(dateOfBirth), active);
    }

    @PutMapping(TRAINEE_ENDPOINT+"/changeLogin")
    public ResponseEntity<Void> changeLogin(@RequestParam("username") String username,
                                            @RequestParam("password") String oldPassword,
                                            @RequestParam("password") String newPassword){
        traineeService.changePassword(username,oldPassword,newPassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTraineeProfile(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        traineeService.delete(username,password);
        return ResponseEntity.ok().build();
    }

    private LocalDate parsingData(String date) {
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
