package hackathon.backend.iplanner.model.events;

import lombok.*;

import java.util.Map;

@Getter
@Setter
public class JoinRoomEvent extends RoomEvent {

    Map<String, RoomEvent> currentPlayers;
    CreateStoryEvent storyInEstimation;
}
