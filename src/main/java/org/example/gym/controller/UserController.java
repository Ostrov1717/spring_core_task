package org.example.gym.controller;

import org.example.gym.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public static final String USER_ENDPOINT = "/user";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USER_ENDPOINT+"/login")
    public ResponseEntity<Void> login(@RequestParam("username") String username, @RequestParam("password") String password){
        userService.authenticate(username,password);
        return ResponseEntity.ok().build();
    }

}
