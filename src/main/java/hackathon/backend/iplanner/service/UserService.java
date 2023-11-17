package hackathon.backend.iplanner.service;
import hackathon.backend.iplanner.model.User;
import hackathon.backend.iplanner.repository.UserRepository;
import hackathon.backend.iplanner.dto.UserRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUser(){
        List<User> users = userRepository.findAll();
        return users;
    }


    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) return user.get();
        return null;
    }


    public void createNewUser(UserRequestDto userRequestDto){
        User user = modelMapper.map(userRequestDto, User.class);
        // TODO: this looks wierd
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) return true;
        return false;
    }

    public boolean usernameExists(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) return true;
        return false;
    }
}
