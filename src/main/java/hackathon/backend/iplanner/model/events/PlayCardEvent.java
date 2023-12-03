package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.EventType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayCardEvent extends RoomEvent{
    @Getter
    @Setter
    public static class PlayCardPayload {
        private int cardValue;
        private String color;
        private String cardType;
    }

    private PlayCardPayload payload;
    private final EventType type = EventType.PLAY_CARD;

}
