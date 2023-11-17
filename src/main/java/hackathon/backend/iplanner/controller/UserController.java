package hackathon.backend.iplanner.controller;

import hackathon.backend.iplanner.dto.UserRequestDto;
import hackathon.backend.iplanner.dto.UserResponseDto;
import hackathon.backend.iplanner.service.TokenService;
import hackathon.backend.iplanner.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;


    public UserController(UserService userService, ModelMapper modelMapper, TokenService tokenService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    // TODO: validate user data
    @PostMapping("/user/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        boolean usernameExists = userService.usernameExists(userRequestDto.getUsername());
        if (usernameExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");

        boolean emailExists = userService.emailExists(userRequestDto.getEmail());
        if (emailExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        userService.createNewUser(userRequestDto);
        return ResponseEntity.ok("User account created successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUser(@RequestParam String username){
        UserResponseDto userRequestDto = modelMapper.map(userService.getUserByUsername(username), UserResponseDto.class);
        if(userRequestDto != null) return ResponseEntity.ok(userRequestDto);
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/user")
    public String token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return token;
    }
}
