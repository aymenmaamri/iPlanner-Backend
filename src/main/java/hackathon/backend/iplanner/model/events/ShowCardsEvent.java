package hackathon.backend.iplanner.model.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowCardsEvent extends RoomEvent{
    private boolean payload;

}
