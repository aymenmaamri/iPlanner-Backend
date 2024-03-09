package hackathon.backend.iplanner.model.events;

import hackathon.backend.iplanner.enums.StoryStatus;
import hackathon.backend.iplanner.model.PokerCard;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerReadinessEvent extends RoomEvent {

    @Getter
    @Setter
    public static class PlayerReadinessPayload {
        private boolean isReady;
        private PokerCard currentCard;
    }
    private PlayerReadinessPayload payload;
}
