package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class LeaveRoomEvent extends RoomEvent {
    List<String> currentPlayers;
}
