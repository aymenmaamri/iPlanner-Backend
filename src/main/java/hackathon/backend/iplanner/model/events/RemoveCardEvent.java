package hackathon.backend.iplanner.model.events;
import hackathon.backend.iplanner.enums.EventType;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveCardEvent extends RoomEvent{
    private final EventType type = EventType.REMOVE_CARD;
}
