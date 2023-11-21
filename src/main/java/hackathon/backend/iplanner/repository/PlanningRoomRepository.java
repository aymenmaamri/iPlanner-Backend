package hackathon.backend.iplanner.repository;

import hackathon.backend.iplanner.model.PlanningRoom;
import hackathon.backend.iplanner.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanningRoomRepository extends MongoRepository<PlanningRoom, ObjectId> {
    List<PlanningRoom> findAll();
    Optional<PlanningRoom> findByRoomName(String roomName);
    Optional<PlanningRoom> findByRoomOwner(User roomOwner);
}
