package hackathon.backend.iplanner.service;
import hackathon.backend.iplanner.data.User;
import hackathon.backend.iplanner.data.UserRepository;
import hackathon.backend.iplanner.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    ArrayList<User> users;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        List<User> user = userRepository.findAll();
        return user;
    }


    public User getUserById(String id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }

    public User getUserByUsername(String username){
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }

    public String getUsernameById(String id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get().getUsername();
    }


    public void createNewUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // encrypt password
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
