package hackathon.backend.iplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "planning_room")
public class PlanningRoom {

    @Id
    private ObjectId roomId;
    private String roomName;
    private String roomOwner;
    private List<String> joinedUsers;
    private int numberOfPlanners;
    private Date creationTime;
    private long keepAliveDurationSeconds;

    @Override
    public String toString() {
        return "PlanningRoom{" +
                " roomName='" + this.roomName + '\'' +
                " joined users are " + this.joinedUsers.toString() +
                " roomOwner is " + this.roomOwner +
                '}';
    }

    public boolean isRoomOwner(String username){
        return this.roomOwner.equals(username);
    }
    public boolean isUserAlreadyJoined(String username) {return this.joinedUsers.contains(username);}
}
