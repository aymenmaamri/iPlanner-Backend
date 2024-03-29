package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import hackathon.backend.iplanner.model.PokerCard;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayCardEvent extends RoomEvent{
    private PokerCard payload;

}
