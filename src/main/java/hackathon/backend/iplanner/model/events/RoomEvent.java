package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.utils.StringUtils;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class RoomEvent {
    @Id
    private ObjectId eventId;
    private String roomName;
    private String sender;
    private EventType type;
    private String timeStamp;

    // TODO: can i only set the time in the constructor
    public RoomEvent(ObjectId eventId, String roomName, String sender, EventType type, String timeStamp) {
        System.out.println("executing constructor");
        this.eventId = new ObjectId();
        this.roomName = roomName;
        this.sender = sender;
        this.type = type;
        this.timeStamp = StringUtils.getCurrentTimeStamp();
    }

    public RoomEvent(){
        //System.out.println("no args const is called");
    }
}