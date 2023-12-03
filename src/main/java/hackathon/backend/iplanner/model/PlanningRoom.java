package hackathon.backend.iplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static hackathon.backend.iplanner.utils.PlanningRoomUtils.initializePlayerPositions;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "planning_room")
public class PlanningRoom {

    @Id
    private ObjectId roomId;
    private String roomName;
    private String roomOwner;
    private List<String> joinedUsers;
    // TODO: should i combine joinedUsers and playerPositions into one data structure?
    // should this be here?
    private Map<String, PlayerPositionOnBoard> playerPositions = initializePlayerPositions();

    private List<Story> planningStories;
    private int numberOfPlanners;
    private Date creationTime;
    private long keepAliveDurationSeconds;
    public static final int MAX_PLAYERS_PER_ROOM = 7;


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

    public boolean canJoinRoom() {
        // Check if there is room for another user to join
        return joinedUsers.size() < MAX_PLAYERS_PER_ROOM;
    }

    // TODO: should this be here or in the service?
    public void removePlayer(String username){
        this.joinedUsers.remove(username);
    }

    public boolean isAlreadyAssignedAPosition(String username) {
        return this.playerPositions.values()
                .stream()
                .anyMatch(position -> position.getUsername().equals(username));
    }

    public void freeBoardPosition(String username){
        for (Map.Entry<String, PlayerPositionOnBoard> entry : playerPositions.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                // Found the entry with the matching username, replace the value, is using new the best practice here?
                entry.setValue(new PlayerPositionOnBoard("", false, entry.getValue().getPosition()));
                break;
            }
        }
    }

}

