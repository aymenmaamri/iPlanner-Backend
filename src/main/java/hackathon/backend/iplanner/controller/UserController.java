package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.UserDto;
import hackathon.backend.iplanner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: validate user data
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        System.out.println("endpoint has been hit!");
        boolean usernameExists = userService.usernameExists(userDto.getUsername());
        if (usernameExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");

        boolean emailExists = userService.emailExists(userDto.getEmail());
        if (emailExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        userService.createNewUser(userDto);
        return ResponseEntity.ok("User account created successfully");
        }
    }
