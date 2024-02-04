package hackathon.backend.iplanner.model;

import hackathon.backend.iplanner.model.events.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "planning_room")
public class PlanningRoom {

    @Id
    private ObjectId roomId;
    private String roomName;
    private String roomOwner;
    // This field is set by the user, this number msut be between 2 and 7
    private int maxRoomPlannersNumber;

    //TODO: Implement later
    // private long keepAliveDurationSeconds;
    private Map<String, UserEvent> userEvents;

    // TODO: should this be here, this constraint represents the maximal number of players a room can have
    public static final int MAX_PLAYERS_PER_ROOM = 7;

    public boolean isOwner(String username){
        return this.roomOwner.equals(username);
    }
    public boolean isUserCurrentlyJoined(String username) {
        UserEvent userEvent = userEvents.get(username);

        if (userEvent == null || userEvent.getEventList().isEmpty()) {
            // If the event list is empty, the user has not joined
            return false;
        }

        List<RoomEvent> eventList = userEvent.getEventList();
        for (int i = eventList.size() - 1; i >= 0; i--) {
            RoomEvent event = eventList.get(i);
            if (event instanceof JoinRoomEvent) {
                // User is joined
                return true;
            } else if (event instanceof LeaveRoomEvent) {
                // User has left
                return false;
            }
        }
        return false;
    }

   public boolean isJoinable() {
        // think about name
        int totalJoinedUsers = userEvents.size();
        if (totalJoinedUsers < maxRoomPlannersNumber) return true;

        // Check if there is room for another user to join
       int currentJoinedUsers = (int) userEvents.values().stream()
               .filter(userEvent -> isUserCurrentlyJoined(userEvent.getUser().getUsername()))
               .count();

       if (currentJoinedUsers < maxRoomPlannersNumber) return true;
       return false;
    }

    public Map<String, RoomEvent> getCurrentState() {
        Map<String, RoomEvent> currentPlayersState = new HashMap<String, RoomEvent>();
        List<String> currentPlayers = getCurrentPlayers();
        // get the last sent event for each current player
        for (String player: currentPlayers){
            RoomEvent lastPlayerEvent = getPlayerLastEvent(player);
            currentPlayersState.put(player, lastPlayerEvent);
        }

        return currentPlayersState;
    }

    public List<String> getCurrentPlayers() {
        return userEvents.keySet().stream()
                .filter(username -> isUserCurrentlyJoined(username))
                .collect(Collectors.toList());
    }

    private RoomEvent getPlayerLastEvent(String player) {
        List<RoomEvent> userEvent = userEvents.get(player).getEventList();
        // If event is of type join, return null to avoid infinite recursion exception
        if (userEvent.get(userEvent.size() - 1) instanceof JoinRoomEvent) return null;
        return userEvent.get(userEvent.size() - 1);
    }

    public List<CreateStoryEvent> getStories() {
        List<CreateStoryEvent> roomStories = userEvents.values().stream()
                .flatMap(userEvent -> userEvent.getEventList().stream())
                .filter(event -> event instanceof CreateStoryEvent)
                .map(event -> (CreateStoryEvent) event)
                .collect(Collectors.toList());
        return roomStories;
    }

    // Check if the room owner has a story in estimation
    public CreateStoryEvent getCurrentStoryInEstimation() {
        List<RoomEvent> userEventList = userEvents.get(this.roomOwner).getEventList();
        for (int i = userEventList.size() - 1; i >= 0; i--) {
            if (userEventList.get(i) instanceof CreateStoryEvent) return (CreateStoryEvent) userEventList.get(i);
            if (userEventList.get(i) instanceof EstimateStoryEvent) return null;
        }
        return null;
    }
}

