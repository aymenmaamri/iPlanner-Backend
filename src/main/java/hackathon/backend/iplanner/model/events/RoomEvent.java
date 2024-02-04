package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.utils.StringUtils;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class RoomEvent {
    @Id
    private String eventId;
    private String roomName;
    private String sender;
    private EventType type;
    private String timeStamp;

    // TODO: can i only set the time in the constructor
    public RoomEvent( String roomName, String sender, EventType type) {
        this.eventId = new ObjectId().toString();
        this.roomName = roomName;
        this.sender = sender;
        this.type = type;
        this.timeStamp = StringUtils.getCurrentTimeStamp();
    }
}