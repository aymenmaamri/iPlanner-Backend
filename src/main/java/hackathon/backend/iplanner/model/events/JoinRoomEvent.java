package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.model.PlayerPositionOnBoard;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRoomEvent extends RoomEvent {

    // TODO: should i combine this infos?
    private Map<String, PlayerPositionOnBoard> payload;
    private PlayerPositionOnBoard positionOnBoard;
    private final EventType type = EventType.JOIN_ROOM;


}
