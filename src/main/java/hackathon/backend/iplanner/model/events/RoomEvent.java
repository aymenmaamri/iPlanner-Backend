package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.utils.StringUtils;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class RoomEvent {
    private String roomName;
    private String sender;
    private String payload;
    private EventType type;
    private String time;

    // TODO: can i only set the time in the constructor
    public RoomEvent(String roomName, String sender,String payload, EventType type, String time) {
        this.roomName = roomName;
        this.sender = sender;
        this.payload = payload;
        this.type = type;
        this.time = StringUtils.getCurrentTimeStamp();
    }
}