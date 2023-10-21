package hackathon.backend.iplanner.service;
import hackathon.backend.iplanner.model.User;
import hackathon.backend.iplanner.repository.UserRepository;
import hackathon.backend.iplanner.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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


    public void createNewUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // TODO: encrypt password
        user.setPassword(userDto.getPassword());
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
