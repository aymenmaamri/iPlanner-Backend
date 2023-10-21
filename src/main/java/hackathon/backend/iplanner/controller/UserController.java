package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.UserDto;
import hackathon.backend.iplanner.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // TODO: validate user data
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto){
        boolean usernameExists = userService.usernameExists(userDto.getUsername());
        if (usernameExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");

        boolean emailExists = userService.emailExists(userDto.getEmail());
        if (emailExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        userService.createNewUser(userDto);
        return ResponseEntity.ok("User account created successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam String username){
        UserDto userDto = modelMapper.map(userService.getUserByUsername(username), UserDto.class);
        if(userDto != null) return ResponseEntity.ok(userDto);
        return ResponseEntity.badRequest().body(null);
    }
}
