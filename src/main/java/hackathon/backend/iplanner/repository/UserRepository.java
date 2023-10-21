package hackathon.backend.iplanner.repository;

import hackathon.backend.iplanner.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findAll();
    Optional<User> findByEmail(String Email);
    Optional<User> findByUsername(String username);
}
